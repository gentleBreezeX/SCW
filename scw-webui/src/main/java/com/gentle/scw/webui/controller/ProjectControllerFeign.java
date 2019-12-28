package com.gentle.scw.webui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.service.ProjectServiceFeign;
import com.gentle.scw.webui.vo.response.ProjectVo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class ProjectControllerFeign {

	@Autowired
	private ProjectServiceFeign projectServiceFeign;
	
	@GetMapping("/project/info/{id}")
	public String projectDetails(@PathVariable("id")Integer id, Model model) {
		//远程调用project服务
		AppResponse<ProjectVo> response = projectServiceFeign.detailsInfo(id);
		//共享到request域中
		log.info("查询id={}的项目详情：{}", id, response.getData());
		model.addAttribute("project", response.getData());
		//转发到页面中显示
		return "project/project";
		
	}
}
