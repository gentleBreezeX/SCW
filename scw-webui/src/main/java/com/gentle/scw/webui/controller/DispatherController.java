package com.gentle.scw.webui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.service.ProjectServiceFeign;
import com.gentle.scw.webui.vo.response.ProjectVo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class DispatherController {

	@Autowired
	private ProjectServiceFeign projectServiceFeign;
	
	//跳转首页的方法
	@RequestMapping({"/", "/index.html", "index"})
	public String toIndexPage(Model model) {
		//准备页面需要的数据
		//远程调用project服务获取项目列表
		AppResponse<List<ProjectVo>> response = projectServiceFeign.all();
		log.info("查询的项目列表："+response.getData());
		model.addAttribute("projects", response.getData());
		return "index";
	}
	
	//跳转登录页面的方法
	@RequestMapping("/login.html")
	public String toLoginPage() {
		return "user/login";
	}
}
