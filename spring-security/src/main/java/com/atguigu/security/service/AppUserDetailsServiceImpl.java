package com.atguigu.security.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.Collection;
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


@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username：用户登录提交的账号
		//1.从数据库中根据账号查询用户信息
		String sql = "select id,username,loginacct,userpswd,email from t_admin where loginacct=?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, username);

//		2.如果用户信息查询成功，从数据库中查询该用户的权限角色信息
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (map.get("id") != null) {
			//用户的权限集合应该包含角色列表+权限列表
			sql = "select t_admin_role.roleid, t_role.`name` from t_admin_role join t_role on t_role.id = t_admin_role.roleid where t_admin_role.adminid = ?;";
			//2.1查询角色列表,然后将角色添加到权限集合中
			List<Map<String, Object>> roles = jdbcTemplate.queryForList(sql, map.get("id"));
			for (Map<String, Object> m : roles) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + m.get("name").toString()));
				
				//2.2 查询权限列表，然后将权限添加到权限集合中
				sql = "select t_role_permission.permissionid, t_permission.`name` from t_role_permission join t_permission on t_role_permission.permissionid = t_permission.id where t_role_permission.roleid = ? and  t_permission.`name` is not null";
				List<Map<String, Object>> permissions = jdbcTemplate.queryForList(sql, m.get("roleid"));
				for (Map<String, Object> permission : permissions) {
					authorities.add(new SimpleGrantedAuthority(permission.get("name").toString()));
				}
			}
			
			System.out.println(authorities);
		}
		
		
		
		
		
			
		//		3.封装主体信息返回(登录的账号，数据库中的密码，权限集合 如果表示角色需要添加ROLE_前缀)
		return new User(username, map.get("userpswd").toString(), authorities);
		
	}

}
