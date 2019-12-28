package com.gentle.scw.webui.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.service.UserServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserControllerFeign {

	@Autowired
	private UserServiceFeign userServiceFeign;
	
	@PostMapping("/user/doLogin")
	public String doLogin(HttpSession session, String loginacct, String userpswd, Model model) {
		//由于用户操作业务在scw-user中已经编写
		AppResponse<Object> response = userServiceFeign.doLogin(loginacct, userpswd);
		log.info("返回值信息：{}", response);
		if (response!=null && response.getCode()==10000) {
			//登录成功,保存登录状态,是spring session
			session.setAttribute("member", response.getData());
			//如果是从结账页面跳转登录的，需要跳转登录之前的页面
			String path = (String) session.getAttribute("path");
			if (StringUtils.isEmpty(path)) {
				return "redirect:/index.html";				
			}else {
				return "redirect:" + path;
			}
			
		}else {
			model.addAttribute("loginErrorMsg", "账号或密码错误");
			model.addAttribute("loginacct",loginacct);
			return "user/login";
		}
	}
	
	
}
