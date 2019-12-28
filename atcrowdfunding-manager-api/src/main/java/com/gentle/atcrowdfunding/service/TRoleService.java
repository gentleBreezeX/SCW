package com.gentle.atcrowdfunding.service;

import java.util.List;

import javax.management.relation.Role;

import com.gentle.atcrowdfunding.bean.TRole;

public interface TRoleService {

	List<TRole> listRoles(String condition);

	void deleteRoleById(Integer id);

	void saveRole(TRole role);

	TRole getRoleById(Integer id);

	void updateRole(TRole role);

	List<Integer> getRolePermissionIds(Integer id);

	void deleteRolePermissions(Integer id);

	void updateRolePermissions(Integer id, List<Integer> pids);

}
