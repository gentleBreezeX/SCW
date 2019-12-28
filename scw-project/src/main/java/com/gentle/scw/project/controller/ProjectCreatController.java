package com.gentle.scw.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.gentle.scw.common.consts.AppConsts;
import com.gentle.scw.common.templates.OSSTemplate;
import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.common.utils.SCWUtils;
import com.gentle.scw.project.bean.TMember;
import com.gentle.scw.project.bean.TReturn;
import com.gentle.scw.project.service.ProjectService;
import com.gentle.scw.project.vo.request.ProjectBaseInfoVo;
import com.gentle.scw.project.vo.request.ProjectLegalPropleInfoVo;
import com.gentle.scw.project.vo.request.ProjectRedisStorageVo;
import com.gentle.scw.project.vo.request.ProjectReturnVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "处理用户创建发布项目的controller")
@RestController
public class ProjectCreatController {

	@Autowired
	private OSSTemplate ossTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private ProjectService projectService;

	@ApiOperation("处理用户发起项目众筹，第三步的收集法人信息并提交")
	@PostMapping("/project/creat/submitProject")
	public AppResponse<Object> stepThree(Integer submitType, ProjectLegalPropleInfoVo legalInfoVo) {
		// 验证用户是否登录
		if (StringUtils.isEmpty(legalInfoVo.getAccessToken())) {
			return AppResponse.fail(null, "发布众筹项目必须必须要登录！");
		}
		String memberStr = stringRedisTemplate.opsForValue()
				.get(AppConsts.MEMBER_PREFIX + legalInfoVo.getAccessToken() + AppConsts.MEMBER_SUFFIX);
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录！");
		}
		// submitType：提交的类型 0代表草稿 1代表提交
		if (submitType == 1) {
			// 获取redis中缓存的bigVo对象，将法人信息存进去
			ProjectRedisStorageVo bigVo = SCWUtils.getBeanFromRedis(stringRedisTemplate, legalInfoVo.getProjectToken(),
					ProjectRedisStorageVo.class);
			BeanUtils.copyProperties(legalInfoVo, bigVo);
			//将bigVo的数据转换为数据库表对应的JavaBean对象，然后存入数据库
			projectService.saveProject(bigVo);
			//删除redis中的项目缓存
			stringRedisTemplate.delete(AppConsts.TEMP_PROJECT_PREFIX + legalInfoVo.getProjectToken());
			return AppResponse.ok(bigVo, "项目发布成功");
		} else {
			return AppResponse.ok(null, "草稿保存成功");
		}
	}

	@ApiOperation("处理用户发起项目众筹，第二步的收集回报信息")
	@PostMapping("/project/creat/stepTwo")
	public AppResponse<Object> stepTwo(@RequestBody List<ProjectReturnVo> returnVos) {
		// 验证用户是否登录
		if (CollectionUtils.isEmpty(returnVos)) {
			return AppResponse.fail(null, "请上传回报信息！");
		}

		ProjectReturnVo returnVo = returnVos.get(0);
		if (StringUtils.isEmpty(returnVo.getAccessToken())) {
			return AppResponse.fail(null, "发布众筹项目必须必须要登录！");
		}

		String memberStr = stringRedisTemplate.opsForValue()
				.get(AppConsts.MEMBER_PREFIX + returnVo.getAccessToken() + AppConsts.MEMBER_SUFFIX);
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录！");
		}
		// 获取redis中存储的bigVo对象
		String projectToken = returnVo.getProjectToken();
		ProjectRedisStorageVo bigVo = SCWUtils.getBeanFromRedis(stringRedisTemplate, projectToken,
				ProjectRedisStorageVo.class);
		// 将ProjectReturnVo集合转换为Treturn集合设置给bigVo
		// 将接收到的回报的vo对象集合转为Treturn集合
		ArrayList<TReturn> list = new ArrayList<TReturn>();
		for (ProjectReturnVo projectReturnVo : returnVos) {
			TReturn tReturn = new TReturn();
			BeanUtils.copyProperties(projectReturnVo, tReturn);
			list.add(tReturn);
		}
		bigVo.setProjectReturns(list);
		// 将修改后的bigVo设置到redis中替换之前的
		String bigVoStr = JSON.toJSONString(bigVo);
		stringRedisTemplate.opsForValue().set(AppConsts.TEMP_PROJECT_PREFIX + bigVo.getProjectToken(), bigVoStr, 1,
				TimeUnit.HOURS);
		return AppResponse.ok(bigVo, "回报信息上传成功");
	}

	@ApiOperation("处理用户发起项目众筹，第一步的方法")
	@PostMapping("/project/creat/stepOne")
	public AppResponse<Object> stepOne(ProjectBaseInfoVo baseInfoVo) {
		// 验证用户是否登录
		if (StringUtils.isEmpty(baseInfoVo.getAccessToken())) {
			return AppResponse.fail(null, "发布众筹项目必须必须要登录！");
		}
		String memberStr = stringRedisTemplate.opsForValue()
				.get(AppConsts.MEMBER_PREFIX + baseInfoVo.getAccessToken() + AppConsts.MEMBER_SUFFIX);
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录！");
		}
		// 从redis中获取缓存的bigVo
		ProjectRedisStorageVo bigVo = SCWUtils.getBeanFromRedis(stringRedisTemplate, baseInfoVo.getProjectToken(),
				ProjectRedisStorageVo.class);
		// 将收集的数据拷贝给bigVo对象
		BeanUtils.copyProperties(baseInfoVo, bigVo);
		// 将修改后的bigVo设置到redis中替换之前的
		String bigVoStr = JSON.toJSONString(bigVo);

		log.info("第一步数据封装之后bigVo对象:{}", bigVo);
		stringRedisTemplate.opsForValue().set(AppConsts.TEMP_PROJECT_PREFIX + bigVo.getProjectToken(), bigVoStr, 1,
				TimeUnit.HOURS);

		return AppResponse.ok(bigVo, "第一步：发起项目的信息添加成功");
	}

	@ApiOperation("处理用户发起项目众筹，阅读并同意协议的方法")
	@PostMapping("/project/creat/initProject")
	public AppResponse<Object> initProject(String accessToken) {
		// 验证用户是否登录
		if (StringUtils.isEmpty(accessToken)) {
			return AppResponse.fail(null, "发布众筹项目必须必须要登录！");
		}
		String memberStr = stringRedisTemplate.opsForValue()
				.get(AppConsts.MEMBER_PREFIX + accessToken + AppConsts.MEMBER_SUFFIX);
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录！");
		}
		// 创建用户存储项目发布所有信息的bean对象
		ProjectRedisStorageVo bigVo = new ProjectRedisStorageVo();
		bigVo.setAccessToken(accessToken);
		// 将redis中查询的json字符串转化为对应的对象
		TMember member = JSON.parseObject(memberStr, TMember.class);
		bigVo.setMemberid(member.getId());
		// 生成一个projectToken
		String projectToken = UUID.randomUUID().toString().replace("-", "");
		bigVo.setProjectToken(projectToken);
		// 将bigVo存储在redis中，超时时间是1小时
		String bigVoStr = JSON.toJSONString(bigVo);
		stringRedisTemplate.opsForValue().set(AppConsts.TEMP_PROJECT_PREFIX + projectToken, bigVoStr, 1,
				TimeUnit.HOURS);
		return AppResponse.ok(bigVo, "项目初始化成功");

	}

	@ApiOperation("处理用户创建项目时上传图片的请求")
	@ApiImplicitParam(name = "imgs", value = "上传的图片集合")
	@PostMapping("/project/creat/uploadImgs")
	public AppResponse<Object> uploadImgs(MultipartFile[] imgs) {
		// 如果接受的图片数组为null
		if (imgs != null) {
			// 图片上传成功，保存图片路径的集合
			List<String> paths = new ArrayList<>();
			// 统计上传成功的图片数量
			int count = 0;
			for (MultipartFile img : imgs) {
				try {
					String path = ossTemplate.uploadImg(img);
					paths.add(path);
					count++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 给用户响应
			return AppResponse.ok(paths, "上传成功：" + count + "张，" + "失败：" + (imgs.length - count) + "张");
		}
		return AppResponse.fail(null, "没有读取到上传的文件");
	}
}
