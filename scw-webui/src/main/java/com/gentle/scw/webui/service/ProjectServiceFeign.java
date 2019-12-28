package com.gentle.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gentle.scw.common.utils.AppResponse;
import com.gentle.scw.webui.vo.response.ProjectVo;
import com.gentle.scw.webui.vo.response.ReturnPayConfirmVo;
@FeignClient(value = "SCW-PROJECT")
public interface ProjectServiceFeign {

	@GetMapping("/project/info/all")
	public AppResponse<List<ProjectVo>> all();
	
	@GetMapping("/project/details/info/{projectId}")
	public AppResponse<ProjectVo> detailsInfo(@PathVariable("projectId") Integer projectId);
	
	@GetMapping("/project/return/info")
	public AppResponse<ReturnPayConfirmVo> getReturnInfo(@RequestParam("returnId") Integer returnId);
}
