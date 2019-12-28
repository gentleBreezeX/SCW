package com.gentle.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gentle.atcrowdfunding.bean.TPermission;
import com.gentle.atcrowdfunding.mapper.TPermissionMapper;
import com.gentle.atcrowdfunding.service.TPermissionService;
@Service
public class TPermissionServiceImpl implements TPermissionService {

	@Autowired
	private TPermissionMapper permissionMapper;
	
	@Override
	public List<TPermission> listAllPermissions() {
		
		return permissionMapper.selectByExample(null);
	}

}
