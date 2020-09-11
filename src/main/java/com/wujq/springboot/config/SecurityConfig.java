package com.wujq.springboot.config;

import com.wujq.springboot.component.JwtAuthenticationTokenFilter;
import com.wujq.springboot.component.RestAuthenticationEntryPoint;
import com.wujq.springboot.component.RestfulAccessDeniedHandler;
import com.wujq.springboot.dto.AdminUserDetails;
import com.wujq.springboot.mbg.model.UmsAdmin;
import com.wujq.springboot.mbg.model.UmsPermission;
import com.wujq.springboot.service.UmsAdminService;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UmsAdminService umsAdminService;

	@Autowired
	public RestfulAccessDeniedHandler restfulAccessDeniedHandler;

	@Autowired
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				//基于token，所以不需要session
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(String.valueOf(HttpMethod.GET),
						"/",
						"/*.html",
						"/favicon.ico",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js",
						"/swagger-resources/**",
						"/v2/api-docs/**")
				.permitAll()
				//对登录注册要允许
				.antMatchers("/admin/login","/admin/register")
				//测试时全部运行访问
				.permitAll().
				antMatchers("/**")
				.permitAll()
				.
				//除上面外所有的请求全部需要鉴权认证
				anyRequest().authenticated();
		//禁用缓存
		http.headers().cacheControl();
		http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//添加自定义未授权和未登录结果返回
		http.exceptionHandling()
				.accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthenticationEntryPoint);


	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		//获取登录用户信息
		return username -> {
			UmsAdmin admin = umsAdminService.getAdminByUsername(username);
			if (admin != null) {
				List<UmsPermission> permissionList = umsAdminService.getPermissionList(admin.getId());
				return new AdminUserDetails(admin,permissionList);
			}
			throw new UsernameNotFoundException("用户名或密码错误");
		};
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
