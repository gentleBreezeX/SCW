package com.gentle.scw.webui.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.service.UserServiceFeign;
import com.gentle.scw.webui.vo.response.TMemberAddress;
@Component
public class UserServiceFeignExceptionHandler implements UserServiceFeign {

	@Override
	public AppResponse<Object> doLogin(String loginacct, String userpswd) {
		//远程服务调用失败
		
		return AppResponse.fail(null, "远程服务调用失败");
	}

	@Override
	public AppResponse<List<TMemberAddress>> addressInfo(String accessToken) {
		return AppResponse.fail(null, "远程服务调用失败");
	}

}
