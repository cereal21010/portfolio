package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JpaSecurityUser extends User{

	private Member member;
	
	public JpaSecurityUser(Member member) {
		super(member.getId(), "{noop}"+member.getPassword(), makeGrantedAuthority(member.getRole()));
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(String role){
		List<GrantedAuthority> list = new ArrayList<>();
		
		list.add(new SimpleGrantedAuthority(role));
		
		return list;
	}

}
