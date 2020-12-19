package com.jpa.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seq;
	private String id;
	private String name;
	private String password;
	private String role;
	
	//==member 생성메소드==//
	public static Member createMember(String id, String name, String password, String role) {
		Member member = new Member();
		member.setId(id);
		member.setName(name);
		member.setPassword(password);
		member.setRole("ROLE_"+role);
		
		return member;
	}
}
