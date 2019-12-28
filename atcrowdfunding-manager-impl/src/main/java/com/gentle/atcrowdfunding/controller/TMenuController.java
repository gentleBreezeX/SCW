package com.gentle.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gentle.atcrowdfunding.bean.TMenu;
import com.gentle.atcrowdfunding.service.TMenuService;

@Controller
public class TMenuController {

	@Autowired
	private TMenuService menuService;
	
	@RequestMapping("/menu/updateMenu")
	@ResponseBody
	public String updateMenu(TMenu menu) {
		menuService.updateMenu(menu);
		return "ok";
	}
	
	@RequestMapping("/menu/getMenu")
	@ResponseBody
	public TMenu getMenu(Integer id) {
		
		TMenu menu = menuService.getMenu(id);
		
		return menu;
	}
	
	@RequestMapping("/menu/saveMenu")
	@ResponseBody
	public String saveMenu(TMenu menu) {
		menuService.saveMenu(menu);
		
		return "ok";
	}
	
	@RequestMapping("/menu/deleteMenu")
	@ResponseBody
	public String deleteMenu(Integer id) {
		menuService.deleteMenu(id);
		return "ok";
	}
	
	
	@RequestMapping("/menu/index.html")
	public String toMenuPage() {
		return "menu/menu";
	}
	
	@GetMapping("/menu/listMenus")
	@ResponseBody
	public List<TMenu> listMenus() {
		List<TMenu> menus = menuService.listMenus();
		return menus;
	}
}
