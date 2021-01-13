package com.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PageInfo {

	String cover;
	int value;
	String enabled;
	
	@Builder
	public PageInfo(String cover, int value, String enabled) {
		this.cover = cover;
		this.value = value;
		this.enabled = enabled;
	}
}
