package com.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JpaUserService jpaUserService;
	@Autowired
    private AuthenticationSuccessHandler formAuthenticationSuccessHandler;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		log.info("security config................");
		
		http
			.authorizeRequests().antMatchers("/board").permitAll()
		.and()
			.authorizeRequests().antMatchers("/board/**").hasRole("MEMBER")
		.and()
			.formLogin().loginPage("/login").successHandler(formAuthenticationSuccessHandler)
		.and()
			.exceptionHandling().accessDeniedPage("/accessDenied")
		.and()
			.logout().logoutUrl("/logout").invalidateHttpSession(true)
		.and()
			.userDetailsService(jpaUserService)
			.csrf().disable();
	
	}

}
