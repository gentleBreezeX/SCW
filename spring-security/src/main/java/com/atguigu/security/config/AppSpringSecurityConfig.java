package com.atguigu.security.config;

import java.io.IOException;
import java.nio.channels.NonReadableChannelException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/*
 * 1.导入练习的maven工程
 * 2.在pom文件中导入springsecurity的依赖【3个】
 * 3.在web.xml中配置springsecurity的代理filter
 * 4.在项目中创建springsecurity的配置类 必须继承WebSecurityConfigurerAdapter
 * 5.让配置类成为组件+启用springsecurity
 * 		@Configuration : 代表配置类注解
 * 		@EnableWebSecurity : 代表启用springsecurity的注解
 * 6.当访问项目的资源时，自动跳转到一个springsecurity自带登录的页面，则代表springsecurity
 * 		已经配置成功
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//控制表单提交+请求认证授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//super.configure(http);
		//自定义认证授权规则
		http.authorizeRequests()
			.antMatchers("/index.jsp","/layui/**").permitAll()//设置首页所有人都可以访问
			//实验6 基于角色和权限的访问控制
//			.antMatchers("/level1/*").hasAnyRole("SE - 软件工程师")
//			.antMatchers("/level2/*").hasAnyRole("BOSS")
//			.antMatchers("/level3/*").hasAnyAuthority("user:add")
			.anyRequest().authenticated();//设置其他所有的请求都需要认证;
	/*
	 *  实验2.1：如果访问未授权的页面，默认显示的是403页面，但是我们希望给用户响应一个 
	 *  springsecurity默认的登录页面
	 *  http.formLogin();
	 */	
		//实验2.2：默认登录页面由框架提供，过于简单，希望跳转到项目自带的登录页面
		//实验3：设置自定义的登录表单提交的action地址，
			//注意：1.action地址和loginProcessingURL地址一致
			//	   2.请求方式必须是post请求
			//	   3.springsecurity考虑安全问题表单提交必须携带token标志，防止表单重复提交和钓鱼网站
		http.formLogin().loginPage("/index.jsp")//设置自定义的登录页面
			.usernameParameter("uname")
			.passwordParameter("pswd")
			.loginProcessingUrl("/dologin")//设置登录请求的URL地址  默认交给springsecurity处理
			.defaultSuccessUrl("/main.html");//设置登录成功跳转的页面
		
		//禁用springsecurity的csfr验证功能
		//http.csrf().disable();
		
		//实验5 ：默认的注销方式   注意：1.请求方式必须是post 2.CSRF如果开启了必须在表单中携带CSRF的token 3.默认请求的URL是 logout
		//实验5.2 自定义注销方式
		http.logout()
			.logoutUrl("/user-logout")//自定义注销的URL地址
			.logoutSuccessUrl("/index.jsp");//注销成功的跳转页面
		
		//实验6.2 自定义异常处理 当无权访问时（出现403时）跳转到自定义页面
		//http.exceptionHandling().accessDeniedPage("/unauthed");
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
			
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				request.setAttribute("resource", request.getServletPath());//访问失败的资源
				//访问失败的错误信息
				request.setAttribute("errorMsg", accessDeniedException.getMessage());
				//转发到错误页面
				request.getRequestDispatcher("/unauthed").forward(request, response);
			}
		});
		//实验7： 记住我 简单版 【登录请求中携带remeber-me参数，代码中开启remeberme功能】
		//用户登录成功，主体信息（用户信息+权限角色信息）默认保存在服务器内存的session中，一次会话有效
		//如果希望登录之后主体权限角色信息范围超过一次会话，可以开启springsecurity的记住我功能
		//http.rememberMe();//浏览器会接收到springsecurity创建的remeberme的token持久化保存，下次打开浏览器只需要携带token就可以访问之前有权访问的页面
		//服务器将token对应的权限信息存在服务器内存中，如果服务器重启，则记住我功能失效
		//实验7.2  记住我  数据库版
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		http.rememberMe().tokenRepository(tokenRepository);
		
		}
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;
	//PasswordEncoder passwordEncoder = new AppMd5PasswordEncoder();
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//认证：设置验证的账号密码+该用户的角色权限
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth); 框架自带的授权认证规则
		//实验四：自定义认证授权
//		auth.inMemoryAuthentication()//在内存中设置账号密码+授权
//			.withUser("lisi").password("123456").roles("ADMIN","BOSS")//设置一个账号密码
//			.and()
//			.withUser("zhangsan").password("123456").authorities("USER:ADD");
			//设置角色权限时，无论是调用roles还是调用authorities，底层都是调用authorities实现的
			//但是role传入的字符串前默认拼接：ROLE_前缀，表示角色，底层判断角色权限时本质是进行字符串比较的
		
		
		//实验8  基础数据库的数据认证（登录信息和数据库的信息进行比较，登录成功用户的权限角色从数据库中获取）
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);//如果使用userDetailsService框架提供好的实现类封装，则表的信息要和它提供的一样
		
	}
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
