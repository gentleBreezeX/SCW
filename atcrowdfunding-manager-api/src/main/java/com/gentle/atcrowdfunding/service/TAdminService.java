package com.gentle.atcrowdfunding.service;

import java.util.List;

import com.gentle.atcrowdfunding.bean.TAdmin;

public interface TAdminService {
	
	/**
	 * 实现管理员登录的功能
	 * @param loginacct
	 * @param userpswd
	 * @return
	 */
	TAdmin doLogin(String loginacct, String userpswd);

	List<TAdmin> listAdmins(String condition);

	void deleteAdmin(Integer id);

	void saveAdmin(TAdmin admin);

	TAdmin checkAdminById(Integer id);

	void updateAdminById(TAdmin admin);

	void deleteAdmins(List<Integer> ids);
	
	List<Integer> listRolesIdsByAdminId(Integer id);

	void assignRoleToAdmin(Integer adminId, List<Integer> roleId);

	void unAssignRoleToAdmin(Integer adminId, List<Integer> roleId);

}
