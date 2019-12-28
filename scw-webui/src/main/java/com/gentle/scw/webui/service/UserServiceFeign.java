package com.gentle.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.service.impl.UserServiceFeignExceptionHandler;
import com.gentle.scw.webui.vo.response.TMemberAddress;
@FeignClient(value = "SCW-USER", fallback = UserServiceFeignExceptionHandler.class)
public interface UserServiceFeign {

	@PostMapping("/user/doLogin")
	public AppResponse<Object> doLogin(@RequestParam("loginacct")String loginacct, @RequestParam("userpswd")String userpswd);
	
	@PostMapping("/user/info/address")
	public AppResponse<List<TMemberAddress>> addressInfo(@RequestParam("accessToken") String accessToken);
}
