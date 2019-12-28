package com.gentle.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gentle.atcrowdfunding.bean.TPermission;
import com.gentle.atcrowdfunding.service.TPermissionService;

@Controller
public class TPermissionController {

	@Autowired
	private TPermissionService permissionService;
	
	@RequestMapping("/permission/getPermissions")
	@ResponseBody
	public List<TPermission> getPermissions() {
		return permissionService.listAllPermissions();
	}
}
