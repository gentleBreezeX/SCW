package com.gentle.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gentle.atcrowdfunding.bean.TAdmin;
import com.gentle.atcrowdfunding.bean.TAdminExample;
import com.gentle.atcrowdfunding.bean.TRole;
import com.gentle.atcrowdfunding.mapper.TAdminMapper;
import com.gentle.atcrowdfunding.mapper.TPermissionMapper;
import com.gentle.atcrowdfunding.mapper.TRoleMapper;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	TAdminMapper adminMapper;
	@Autowired
	TRoleMapper roleMapper;
	@Autowired
	TPermissionMapper permissionMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//mapper查询 管理员信息
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list = adminMapper.selectByExample(example );
		if(CollectionUtils.isEmpty(list) || list.size()>1) {
			return null;
		}
		TAdmin admin = list.get(0);//数据库中保存的管理员信息对象
		//mapper查询角色和权限集合
		//查询角色name+id:逆向mapper只能完成单表的增删改查
		List<TRole> roles = roleMapper.selectRolesByAdminId(admin.getId());
		List<GrantedAuthority> authorities  = new ArrayList<GrantedAuthority>();
		//遍历集合将角色名添加到权限集合中
		for (TRole role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
			//根据角色id查询权限name
			List<String> permissionNames = permissionMapper.selectPermissionNamesByRoleId(role.getId());
			for (String name : permissionNames) {
				authorities.add(new SimpleGrantedAuthority(name));
			}
		}
		System.out.println(authorities);
		return new User(admin.getLoginacct(), admin.getUserpswd(), authorities);
	}

}
