package com.gentle.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gentle.atcrowdfunding.bean.TAdmin;
import com.gentle.atcrowdfunding.bean.TAdminExample;
import com.gentle.atcrowdfunding.bean.TAdminExample.Criteria;
import com.gentle.atcrowdfunding.bean.TAdminRoleExample;
import com.gentle.atcrowdfunding.exception.TAdminAcctException;
import com.gentle.atcrowdfunding.mapper.TAdminMapper;
import com.gentle.atcrowdfunding.mapper.TAdminRoleMapper;
import com.gentle.atcrowdfunding.service.TAdminService;
import com.gentle.atcrowdfunding.utils.AppDateUtils;
import com.gentle.atcrowdfunding.utils.MD5Util;
import com.gentle.atcrowdfunding.utils.StringUtil;

@Service
public class TAdminServiceImpl implements TAdminService {

	@Autowired
	private TAdminMapper adminMapper;
	@Autowired
	private TAdminRoleMapper adminRoleMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//根据管理员的id获取所对应的所有角色的id集合
	@Override
	public List<Integer> listRolesIdsByAdminId(Integer id) {
		List<Integer> rolesIds = adminRoleMapper.selectRolesIdsByAdminId(id);
		return rolesIds;
	}
	
	@Override
	public TAdmin doLogin(String loginacct, String userpswd) {
		//将管理员输入的密码进行MD5加密
		userpswd = MD5Util.digest(userpswd);
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct).andUserpswdEqualTo(userpswd);		
		
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		if (CollectionUtils.isEmpty(list) || list.size()!=1) {
			return null;
		}

		return list.get(0);
	}

	@Override
	public List<TAdmin> listAdmins(String condition) {
		
		TAdminExample example = null;
		if (!StringUtil.isEmpty(condition)) {
			example = new TAdminExample();
			example.createCriteria().andLoginacctLike("%" + condition + "%");
			Criteria c1 = example.createCriteria().andUsernameLike("%" + condition + "%");
			Criteria c2 = example.createCriteria().andEmailLike("%" + condition + "%");
			example.or(c1);
			example.or(c2);
		}
		List<TAdmin> admins = adminMapper.selectByExample(example);
		return admins;
	}

	@Override
	public void deleteAdmin(Integer id) {
		adminMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void saveAdmin(TAdmin admin) {
		//按照loginacct或者email查询数据库中是否已经存在
		TAdminExample e = new TAdminExample();
		e.createCriteria().andLoginacctEqualTo(admin.getLoginacct());
		long l = adminMapper.countByExample(e);
		if (l > 0) {
			throw new TAdminAcctException("账号已存在");
		}
		e.clear();
		e.createCriteria().andEmailEqualTo(admin.getEmail());
		l = adminMapper.countByExample(e);
		if (l > 0) {
			throw new TAdminAcctException("邮箱被占用");
		}
		//密码加密
		admin.setUserpswd(passwordEncoder.encode(admin.getUserpswd()));
		admin.setCreatetime(AppDateUtils.getFormatTime());
		adminMapper.insertSelective(admin);
	}

	@Override
	public TAdmin checkAdminById(Integer id) {
		TAdmin admin = adminMapper.selectByPrimaryKey(id);
		return admin;
	}

	@Override
	public void updateAdminById(TAdmin admin) {
		
		//按照loginacct或者email查询数据库中是否已经存在
		TAdminExample e = new TAdminExample();
		e.createCriteria().andLoginacctEqualTo(admin.getLoginacct());
		long l = adminMapper.countByExample(e);
		if (l > 0) {
			throw new TAdminAcctException("账号已存在");
		}
		e.clear();
		e.createCriteria().andEmailEqualTo(admin.getEmail());
		l = adminMapper.countByExample(e);
		if (l > 0) {
			throw new TAdminAcctException("邮箱被占用");
		}
		
		adminMapper.updateByPrimaryKeySelective(admin);
		
	}

	@Override
	public void deleteAdmins(List<Integer> ids) {
		
		TAdminExample example = new TAdminExample();
		
		if (!CollectionUtils.isEmpty(ids)) {
			example.createCriteria().andIdIn(ids);
			adminMapper.deleteByExample(example);
		}
		
		
		
	}

	@Override
	public void assignRoleToAdmin(Integer adminId, List<Integer> roleId) {
		adminRoleMapper.assignRoleToAdmin(adminId,roleId);
		
	}

	@Override
	public void unAssignRoleToAdmin(Integer adminId, List<Integer> roleId) {
		TAdminRoleExample example = new TAdminRoleExample();
		example.createCriteria().andAdminidEqualTo(adminId).andRoleidIn(roleId);
		
		adminRoleMapper.deleteByExample(example);
		
	}

	
}
