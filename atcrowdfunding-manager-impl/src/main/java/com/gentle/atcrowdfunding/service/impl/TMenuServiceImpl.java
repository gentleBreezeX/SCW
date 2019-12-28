package com.gentle.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gentle.atcrowdfunding.bean.TMenu;
import com.gentle.atcrowdfunding.mapper.TMenuMapper;
import com.gentle.atcrowdfunding.service.TMenuService;

@Service
public class TMenuServiceImpl implements TMenuService {

	@Autowired
	private TMenuMapper menuMapper;
	
	@Override
	public List<TMenu> listMenus() {
		
		List<TMenu> menus = menuMapper.selectByExample(null);
		//创建一个存放parent的菜单的map
		Map<Integer, TMenu> parentMenusMap = new HashMap<Integer, TMenu>();
		
		if (CollectionUtils.isEmpty(menus)) {
			return null;
		}
		
		for (TMenu menu : menus) {
			if (menu.getPid() == 0) {
				//将Pid等于0的菜单存入Map集合作为父菜单 （这是两级菜单）
				//并且将id值作为键
				parentMenusMap.put(menu.getId(), menu);
			}
		}
		for (TMenu menu : menus) {
			//判断正在遍历的菜单的pid有没有等于map中键的，即为其子菜单
			TMenu pMenu = parentMenusMap.get(menu.getPid());
			if (pMenu != null) {
				//将子菜单存到childrenMenus属性中
				pMenu.getChildrenMenus().add(menu);
			}
		}
		//返回封装完毕的父菜单集合
		return new ArrayList<TMenu>(parentMenusMap.values());
		
	}

	@Override
	public void deleteMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void saveMenu(TMenu menu) {
		menuMapper.insertSelective(menu);
		
	}

	@Override
	public TMenu getMenu(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateMenu(TMenu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
		
	}
	
}
