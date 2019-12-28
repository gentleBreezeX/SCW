package com.gentle.atcrowdfunding.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.MapBasedAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.alibaba.druid.support.json.JSONUtils;

import net.sf.jsqlparser.statement.select.FromItem;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		http.authorizeRequests()
			.antMatchers("/","/index","/index.html", "/static/**").permitAll()//静态资源首页所有人可见
			.anyRequest().authenticated();//其他都需要认证
		//设置和登录相关的配置
		http.formLogin()
			.loginPage("/login.html").permitAll()
		    .usernameParameter("loginacct") 
		    .passwordParameter("userpswd")
		    .loginProcessingUrl("/doLogin") 
		    .defaultSuccessUrl("/main.html");
			
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
			
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				//X-Requested-With: XMLHttpRequest
				//ajax请求
				if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("message", accessDeniedException.getMessage());
					map.put("status", "403");
					response.getWriter().write(JSONUtils.toJSONString(map));
					return;
				}
				request.setAttribute("errorMsg",accessDeniedException.getMessage());
				request.getRequestDispatcher("/error/403.jsp").forward(request, response);
			}
		});
		
		//禁用csrf
		http.csrf().disable();
		//注销配置
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/index");
		
	}
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}
