package com.gentle.atcrowdfunding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gentle.atcrowdfunding.mapper.TMemberMapper;

@Controller
public class DispatcherController {

	@Autowired
	private TMemberMapper memberMapper;
	//跳转到首页
	@RequestMapping(value= {"/" ,"/index" , "/index.html"})
	public String toIndexPage() {
		//测试环境搭建是否完成的
		//System.out.println(memberMapper);
		return "index";
	}
	
	//跳转到登录页面
	@RequestMapping("/login.html")
	public String toLoginPage() {
		return "login";
	}
	
}
