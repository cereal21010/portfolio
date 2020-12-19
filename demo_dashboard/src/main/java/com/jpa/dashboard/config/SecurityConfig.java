package com.jpa.dashboard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JpaUserService jpaUserService;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		log.info("security config................");
		
		http
			.authorizeRequests().antMatchers("/board").permitAll();
		http
			.authorizeRequests().antMatchers("/board/**").hasRole("MEMBER");
		http
			.formLogin().loginPage("/login");
		http
			.exceptionHandling().accessDeniedPage("/accessDenied");
		http
			.logout().logoutUrl("/logout").invalidateHttpSession(true);
		http
			.userDetailsService(jpaUserService);
		
		http
			.csrf().disable();

	
	}
	
	
	
	

}
