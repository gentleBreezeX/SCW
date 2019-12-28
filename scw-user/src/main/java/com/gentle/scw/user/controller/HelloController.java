package com.gentle.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Api(tags = "测试swagger的controller")
@RestController
public class HelloController {

	@ApiOperation(value = "测试的hello方法")
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
//	@ApiOperation("登录方法")
//	@ApiImplicitParams(value = {
//			@ApiImplicitParam(name = "username", required = true, dataTypeClass = String.class),
//			@ApiImplicitParam(name = "password", required = true, defaultValue = "123456")
//	})
	//@PostMapping("/login")
//	public User login(@RequestParam(value = "username" ,required = false)String username, String password) {
//		
//		return new User(100, username, password);
//	}
}
