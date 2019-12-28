package com.gentle.atcrowdfunding.controller;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gentle.atcrowdfunding.bean.TRole;
import com.gentle.atcrowdfunding.consts.AppConsts;
import com.gentle.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/role")
@Controller
public class TRoleController {

	@Autowired
	private TRoleService roleService;
	
	@ResponseBody
	@RequestMapping("/updatePermissions")
	public String updatePermissions(Integer id, @RequestParam("pids")List<Integer> pids) {
		
		//删除角色所有的权限
		roleService.deleteRolePermissions(id);
		//给角色添加修改后的权限
		roleService.updateRolePermissions(id,pids);
		return "ok";
	}
	
	
	@RequestMapping("/getRolePermissionIds")
	@ResponseBody
	public List<Integer> getRolePermissionIds(Integer id) {
		List<Integer> permissionIds = roleService.getRolePermissionIds(id);
		return permissionIds;
	}
	
	//处理更新role的操作
	@PostMapping("/updateRole")
	@ResponseBody
	public String updateRole(TRole role) {
		
		roleService.updateRole(role);
		
		return "ok";
	}
	
	
	//根据id查询一个role的方法
	@RequestMapping("/getRole")
	@ResponseBody
	public TRole getRole(Integer id) {
		
		TRole role = roleService.getRoleById(id);
		
		return role;
	}
	
	
	//处理新增的方法
	@PostMapping("/saveRole")
	@ResponseBody
	public String saveRole(TRole role) {
		//System.out.println(role);
		roleService.saveRole(role);
		
		return "ok";
	}
	
	//处理删除role的方法
	@PreAuthorize("hasRole('XZ - 校长')")
	@PostMapping("/deleteRole")
	@ResponseBody
	public String deleteRole(Integer id) {
		//System.out.println(id);
		roleService.deleteRoleById(id);

		return "ok";
	}
	
	
	//实现ajax请求处理角色维护页面获取role列表的方法
	@GetMapping("/listRoles")
	@ResponseBody
	public PageInfo<TRole> listRoles(@RequestParam(value = "condition", defaultValue = "")String condition,
			@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum){
		//开启分页
		PageHelper.startPage(pageNum, AppConsts.PAGE_SIZE);
		List<TRole> roles = roleService.listRoles(condition);
		
		PageInfo<TRole> pageInfo = new PageInfo<TRole>(roles,AppConsts.NAV_SIZE);
		
		return pageInfo;
		
	}

	//跳转到角色维护页面
	@RequestMapping("/index.html")
	public String toRolePage() {
		
		return "role/role";
	}
	
}
