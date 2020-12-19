package com.jpa.dashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jpa.dashboard.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JpaUserService implements UserDetailsService{
	
	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return
				memberRepository.findById(username)
				.filter(m -> m != null).map(m -> new JpaSecurityUser(m)).get();
	}

	
}
