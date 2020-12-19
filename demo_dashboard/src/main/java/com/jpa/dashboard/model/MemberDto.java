package com.jpa.dashboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

	private String id;
	private String name;
	private String password;
	private String passwordCheck;
}
