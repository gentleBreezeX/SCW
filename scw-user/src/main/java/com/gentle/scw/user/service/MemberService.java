package com.gentle.scw.user.service;


import java.util.List;

import com.gentle.scw.user.bean.TMember;
import com.gentle.scw.user.bean.TMemberAddress;
import com.gentle.scw.user.vo.request.MemberRequestVo;

public interface MemberService {

	void saveMember(MemberRequestVo vo);

	TMember getMember(String phoneNum, String userpswd);

	Boolean checkPhone(String phoneNum);

	List<TMemberAddress> getAllAddress(Integer id);

	

}
