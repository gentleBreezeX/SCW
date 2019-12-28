package com.gentle.atcrowdfunding.service.impl;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gentle.atcrowdfunding.bean.TRole;
import com.gentle.atcrowdfunding.bean.TRoleExample;
import com.gentle.atcrowdfunding.bean.TRolePermissionExample;
import com.gentle.atcrowdfunding.mapper.TRoleMapper;
import com.gentle.atcrowdfunding.mapper.TRolePermissionMapper;
import com.gentle.atcrowdfunding.service.TRoleService;
import com.gentle.atcrowdfunding.utils.StringUtil;

@Service
public class TRoleServiceImpl implements TRoleService {

	@Autowired
	private TRoleMapper roleMapper;
	@Autowired
	private TRolePermissionMapper rolePermissionMapper;
	
	@Override
	public List<TRole> listRoles(String condition) {
		
		TRoleExample example = null;
		
		if (!StringUtil.isEmpty(condition)) {
			example = new TRoleExample();
			example.createCriteria().andNameLike("%"+condition+"%");
				
		}
		
		List<TRole> roles = roleMapper.selectByExample(example);
		
		return roles;
	}

	@Override
	public void deleteRoleById(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void saveRole(TRole role) {
		roleMapper.insertSelective(role);
		
	}

	@Override
	public TRole getRoleById(Integer id) {
		
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateRole(TRole role) {
		roleMapper.updateByPrimaryKeySelective(role);
		
	}

	@Override
	public List<Integer> getRolePermissionIds(Integer id) {
		List<Integer> permissionIds = rolePermissionMapper.selectPermissionIdsByRoleId(id);
		return permissionIds;
	}

	@Override
	public void deleteRolePermissions(Integer id) {
		TRolePermissionExample example = new TRolePermissionExample();
		example.createCriteria().andRoleidEqualTo(id);
		
		rolePermissionMapper.deleteByExample(example);
	}

	@Override
	public void updateRolePermissions(Integer id, List<Integer> pids) {
		rolePermissionMapper.insertRolePermissionIds(id,pids);
		
	}

}
