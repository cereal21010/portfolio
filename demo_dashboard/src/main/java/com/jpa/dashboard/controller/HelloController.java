package com.jpa.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/layoutTest")
	public String layoutTest() {
		return "layoutTest";
	}
	
	
}
