package com.gentle.atcrowdfunding.service;

import java.util.List;

import com.gentle.atcrowdfunding.bean.TMenu;

public interface TMenuService {

	List<TMenu> listMenus();

	void deleteMenu(Integer id);

	void saveMenu(TMenu menu);

	TMenu getMenu(Integer id);

	void updateMenu(TMenu menu);

}
