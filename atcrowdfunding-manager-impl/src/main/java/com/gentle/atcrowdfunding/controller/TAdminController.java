package com.gentle.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gentle.atcrowdfunding.bean.TAdmin;
import com.gentle.atcrowdfunding.bean.TMenu;
import com.gentle.atcrowdfunding.bean.TRole;
import com.gentle.atcrowdfunding.consts.AppConsts;
import com.gentle.atcrowdfunding.mapper.TAdminRoleMapper;
import com.gentle.atcrowdfunding.service.TAdminService;
import com.gentle.atcrowdfunding.service.TMenuService;
import com.gentle.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Controller
public class TAdminController {
	
	@Autowired
	private TAdminService adminService;
	
	@Autowired
	private TMenuService menuService;
	
	@Autowired
	private TRoleService roleService;
	
	//引入slf4j日志
	Logger logger = LoggerFactory.getLogger(getClass());

	//分配角色给admin
	@RequestMapping("/admin/assignRoleToAdmin")
	@ResponseBody
	public String assignRoleToAdmin(Integer adminId, @RequestParam("roleId")List<Integer> roleId) {
		
		adminService.assignRoleToAdmin(adminId,roleId);
		
		return "ok";
	}
	//取消分配角色给admin
	@RequestMapping("/admin/unAssignRoleToAdmin")
	@ResponseBody
	public String unAssignRoleToAdmin(Integer adminId, @RequestParam("roleId")List<Integer> roleId) {
		
		adminService.unAssignRoleToAdmin(adminId,roleId);
		
		return "ok";
	}
	
	
	//跳转到角色分配页面
	@RequestMapping("/admin/assignRole.html")
	public String toAssignRolePage(Integer id, Model model) {
		
		//获取未分配的角色列表和获取已经分配的角色列表
		//1.查询所有的角色集合
		List<TRole> roles = roleService.listRoles(null);
		//2.查询对应id管理员分配的角色集合
		List<Integer> rolesIds = adminService.listRolesIdsByAdminId(id);
		//3.将所有角色的集合分成已分配和未分配的集合
		List<TRole> assignRoles = new ArrayList<TRole>();
		List<TRole> unAssignRoles = new ArrayList<TRole>();
		
		for (TRole role : roles) {
			if (rolesIds.contains(role.getId())) {
				assignRoles.add(role);
			}else {
				unAssignRoles.add(role);
			}
		}
		//4.共享request域中
		model.addAttribute("assignRoles", assignRoles);
		model.addAttribute("unAssignRoles", unAssignRoles);
		
		
		return "admin/assignRole";
	}
	
	
	//处理批量删除用户的方法
	@RequestMapping("/admin/deleteAdmins")
	public String deleteAdmins(@RequestHeader("referer")String referer, 
			@RequestParam("ids")List<Integer> ids) {
		System.out.println(ids);
		adminService.deleteAdmins(ids);
		
		return "redirect:" + referer;
	}
	
	
	//跳转到修改页面
	@RequestMapping("/admin/edit.html")
	public String toEditPage(HttpSession session ,Model model, Integer id, @RequestHeader("referer")String referer) {
		//核实数据库中的该id值的admin的信息
		TAdmin admin = adminService.checkAdminById(id);
		
		model.addAttribute("editAdmin", admin);
		session.setAttribute("editReferer", referer);
		return "admin/edit";
	}
	
	//处理修改用户信息的方法
	@RequestMapping("/admin/updateAdmin")
	public String editAdmin(HttpSession session ,TAdmin admin, Model model) {
		
		String referer = (String)session.getAttribute("editReferer");
		
		try {
			adminService.updateAdminById(admin);
		} catch (Exception e) {
			model.addAttribute("errorMsg", e.getMessage());
			model.addAttribute("editAdmin", admin);
			return "admin/edit";
		}
		
		return "redirect:" + referer;
	}
	
	//跳转新增管理员的页面
	@RequestMapping("/add.html")
	public String toAddPage() {
		return "admin/add";
	}
	
	//处理新增管理员的操作
	@PostMapping("/saveAdmin")
	public String saveAdmin(HttpSession session, Model model, TAdmin admin) {
		try {
			adminService.saveAdmin(admin);			
		} catch (Exception e) {
			model.addAttribute("errorMsg", e.getMessage());
			return "admin/add";
		}
		Integer pages = (Integer) session.getAttribute(AppConsts.PAGES_COUNT);
		return "redirect:/admin/index.html?pageNum=" + (pages+1);
	}
	
	
	//处理删除管理员的方法
	@PreAuthorize("hasRole('XZ - 校长')")
	@RequestMapping("/admin/deleteAdmin")
	public String deleteAdmin(Integer id, @RequestHeader("referer")String referer) {
		adminService.deleteAdmin(id);
		return "redirect:" + referer;
	}
	
	//跳转到“用户维护”页面，并且处理用户维护界面显示管理员列表
	@RequestMapping("/admin/index.html")
	public String toUserPage(HttpSession session, Model model,
			@RequestParam(value="condition", defaultValue="")String condition, 
			@RequestParam(value="pageNum",defaultValue = "1")Integer pageNum) {
		
		//开启分页
		PageHelper.startPage(pageNum, AppConsts.PAGE_SIZE);//参数1：查询的分页页码，参数2：每页显示的条数
		
		//查询所有的管理员
		List<TAdmin> admins = adminService.listAdmins(condition);
		
		//通过PageHelper去解析查询的结果获取更加详细的分页信息(类似web项目中的Page)：
		//包括 页码、总页码、总记录条数、分页数据的集合、分页查询的起始索引...
		PageInfo<TAdmin> pageInfo = new PageInfo<>(admins, AppConsts.NAV_SIZE);//参数1：需要分页的管理员列表，参数2：分页时需要显示的可以点击的页码个数
		//将总页码存入session域中
		session.setAttribute(AppConsts.PAGES_COUNT, pageInfo.getPages());
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "admin/user";
	}
	
	//实现注销功能跳转到首页
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/index";
//	}
	
	// 实现管理员登录功能
	/*
	 * @RequestMapping("/doLogin") public String doLogin(String loginacct, String
	 * userpswd, HttpServletRequest request, HttpSession session) {
	 * 
	 * // 调用service层方法,查询账号是否存在 TAdmin admin = adminService.doLogin(loginacct,
	 * userpswd); // 判断admin的值 if (admin == null) { // 登录失败返回错误信息 String errorMsg =
	 * "账号或密码错误"; request.setAttribute("errorMsg", errorMsg); return "login"; } //
	 * 登录成功，将用户信息封存在session中 session.setAttribute("admin", admin); return
	 * "redirect:/main.html"; }
	 */

	//浏览器需要重定向访问main页面，但是web-inf下的资源不能被浏览器直接访问，提供controller方法
	// 访问管理员后台首页
	@RequestMapping("/main.html")
	public String toMainPage(HttpSession session) {
		//后台首页左侧菜单栏显示  需要菜单的集合
		List<TMenu> parentMenus = menuService.listMenus();
		logger.info("查询到菜单集合：{}", parentMenus);
		//保存菜单到session域中
		session.setAttribute("parentMenus", parentMenus);
		
		return "admin/main";
	}
}
