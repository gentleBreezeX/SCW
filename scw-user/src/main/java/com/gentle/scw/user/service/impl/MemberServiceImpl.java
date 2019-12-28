package com.gentle.scw.user.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gentle.scw.user.bean.TMember;
import com.gentle.scw.user.bean.TMemberAddress;
import com.gentle.scw.user.bean.TMemberAddressExample;
import com.gentle.scw.user.bean.TMemberExample;
import com.gentle.scw.user.mapper.TMemberAddressMapper;
import com.gentle.scw.user.mapper.TMemberMapper;
import com.gentle.scw.user.service.MemberService;
import com.gentle.scw.user.vo.request.MemberRequestVo;
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private TMemberMapper memberMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TMemberAddressMapper tMemberAddressMapper;
	
	@Override
	public void saveMember(MemberRequestVo vo) {
		//先将vo和member一样的值设置给member
		TMember member = new TMember();
		BeanUtils.copyProperties(vo, member);
		//密码加密
		member.setUserpswd(passwordEncoder.encode(member.getUserpswd()));
		//设置其他非空属性值
		member.setUsername(member.getLoginacct());
		member.setAuthstatus("0");//0-未认证  1-认证中  2-已认证
		
		memberMapper.insertSelective(member);
	}


	@Override
	public TMember getMember(String phoneNum, String userpswd) {
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(phoneNum);
		List<TMember> list = memberMapper.selectByExample(example);
		
		if (CollectionUtils.isEmpty(list) || list.size() > 1) {
			return null;
		}
		TMember member = list.get(0);
		//检查密码是否正确
		boolean matches = passwordEncoder.matches(userpswd, member.getUserpswd());
		if (!matches) {
			return null;
		}
		//擦除密码返回对象
		member.setUserpswd("[PROTECTED]");
		return member;
	}


	@Override
	public Boolean checkPhone(String phoneNum) {
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(phoneNum);
		long l = memberMapper.countByExample(example);
		if (l > 0) {
			return false;
		}
		
		return true;
	}


	@Override
	public List<TMemberAddress> getAllAddress(Integer id) {
		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(id);
		return tMemberAddressMapper.selectByExample(example );
	}

}
