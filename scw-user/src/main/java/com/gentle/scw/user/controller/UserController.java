package com.gentle.scw.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gentle.scw.common.consts.AppConsts;
import com.gentle.scw.common.templates.SmsTemplate;
import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.common.utils.SCWUtils;
import com.gentle.scw.user.bean.TMember;
import com.gentle.scw.user.bean.TMemberAddress;
import com.gentle.scw.user.service.MemberService;
import com.gentle.scw.user.vo.request.MemberRequestVo;
import com.gentle.scw.user.vo.response.MemberResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
@Api(tags = "处理用户注册验证码登陆请求的controller")
@RestController
@Slf4j
public class UserController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SmsTemplate smsTemplate;
	@Autowired
	private MemberService memberService;
	
	
	@ApiOperation("查询用户收货地址的方法")
	@PostMapping("/user/info/address")
	public AppResponse<List<TMemberAddress>> addressInfo(@RequestParam("accessToken") String accessToken){
		//根据accessToken去redis中查询用户的登录信息
		String memberStr = stringRedisTemplate.opsForValue()
				.get(AppConsts.MEMBER_PREFIX + accessToken + AppConsts.MEMBER_SUFFIX);
		log.info("查询到的member字符串是：{}", memberStr);
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录！");
		}
		//将memberStr转换为member对象
		TMember member = JSON.parseObject(memberStr, TMember.class);
		//根据memberid查询地址信息
		List<TMemberAddress> address = memberService.getAllAddress(member.getId());
		log.info("被远程调用得到的地址是：{}", address);
		return AppResponse.ok(address, "地址信息查询成功");
	}
	
	
	//3.处理登录请求的方法
	@ApiOperation("处理用户登录请求的方法")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "loginacct", required = true, value = "登录手机号"),
		@ApiImplicitParam(name = "userpswd", required = true, value = "登录密码")
	})
	@PostMapping("/user/doLogin")
	public AppResponse<Object> doLogin(@RequestParam("loginacct")String loginacct, @RequestParam("userpswd")String userpswd) {
		//1.调用service 方法查询用户信息
		TMember member = memberService.getMember(loginacct, userpswd);
		log.info("查询到的信息是：{},{},{}",member,loginacct,userpswd);
		if (member == null) {
			return AppResponse.fail(null, "账号或密码错误");
		}
		//2.查询成功，创建存储信息的键，将用户信息存在redis中
		String memberKey = UUID.randomUUID().toString().replace("-", "");
		String memberJson = JSON.toJSONString(member);
		stringRedisTemplate.opsForValue().set(AppConsts.MEMBER_PREFIX + memberKey + AppConsts.MEMBER_SUFFIX, 
				memberJson, 7, TimeUnit.DAYS);
		//3.返回token给前台
		//响应用户信息给前台项目，可封装对应的vo
		MemberResponseVo responseVo = new MemberResponseVo();
		BeanUtils.copyProperties(member, responseVo);
		responseVo.setAccessToken(memberKey);
		//以后前台系统访问后台系统，只需要携带token，就能在redis中获取用户信息，就代表已经登录
		return AppResponse.ok(responseVo, "登录成功");
	}
	
	
	//2.处理注册请求的方法
	@ApiOperation("处理用户注册请求的方法")
	@PostMapping("/user/doRegist")
	public AppResponse<Object> doRegist(MemberRequestVo vo){
		//1.检查验证码是否正确
		//查询手机号对应的验证码
		String loginacct = vo.getLoginacct();
		String redisCode = stringRedisTemplate.opsForValue().get(AppConsts.CODE_PREFIX + loginacct + AppConsts.CODE_SUFFIX);
		if (StringUtils.isEmpty(redisCode)) {
			return AppResponse.fail(null, "验证码已过期");
		}
		if (!redisCode.equals(vo.getCode())) {
			return AppResponse.fail(null, "验证码错误");
		}
		//2.调用service层方法 注册
		memberService.saveMember(vo);
		//删除redis中验证码
		stringRedisTemplate.delete(AppConsts.CODE_PREFIX + loginacct + AppConsts.CODE_SUFFIX);
		return AppResponse.ok(null, "注册成功");
	}
	
	
	
	//1.给手机号码发送验证码
	@ApiOperation("发送验证码的方法")
	@ApiImplicitParam(name = "phoneNum", required = true, value = "手机号码")
	@PostMapping("/user/sendSms")
	public AppResponse<Object> sendSms(@RequestParam("phoneNum")String phoneNum) {
		boolean b = SCWUtils.isMobilePhone(phoneNum);
		if (!b) {
			return AppResponse.fail(null, "您输入的手机号码不正确");
		}
		//验证redis中存储的当前手机号获取验证码的次数
		//第一次获取没有，或者没有超过指定次数可以继续获取验证码
		//一个手机号码一天内最多获取五次验证码
		String countStr = stringRedisTemplate.opsForValue().get(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX);
		int count = 0;
		if (!StringUtils.isEmpty(countStr)) {
			//如果数量字符串不为空，转为数字
			count = Integer.parseInt(countStr);
		}
		if (count >= 5) {
			return AppResponse.fail(null, "验证码获取次数超出限制");
		}
		//验证redis中当前手机号码是否存在未过期的验证码
		Boolean hasKey = stringRedisTemplate.hasKey(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_SUFFIX);
		if (hasKey) {
			return AppResponse.fail(null, "请不要频繁获取验证码");
		}
		//判断手机号码是否被注册
		Boolean flag = memberService.checkPhone(phoneNum);
		if (!flag) {
			return AppResponse.fail(null, "手机号码已经被占用");
		}
		
		//发送验证码
		String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", phoneNum);
		querys.put("param", AppConsts.CODE_PREFIX + code);
		querys.put("tpl_id", "TP1711063");
		boolean sendCode = smsTemplate.sendCode(querys);
		if (!sendCode) {
			return AppResponse.fail(null, "短信验证码发送失败");
		}
		//将验证码存在redis中5分钟
		stringRedisTemplate.opsForValue().set(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_SUFFIX, code, 5, TimeUnit.MINUTES);
		//修改该手机的验证码的次数
		Long expire = stringRedisTemplate.getExpire(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX, TimeUnit.MINUTES);
		if (expire == null || expire <= 0) {
			expire = (long)(24*60);
		}
		//存入一个获取验证码的次数
		count++;
		stringRedisTemplate.opsForValue().set(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX, count+"", expire, TimeUnit.MINUTES);
		return AppResponse.ok(null, "发送成功");
	}

}
