package com.jpa.dashboard.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpa.dashboard.domain.Member;
import com.jpa.dashboard.model.MemberDto;
import com.jpa.dashboard.repository.MemberRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {
	MemberRepository memberRepository;

	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "/login/register";
	}
	
	@PostMapping("/register")
	public String registerSave(MemberDto dto) {
		Member member = Member.createMember(dto.getId(), dto.getName(), dto.getPassword(), "MEMBER");
		memberRepository.save(member);
		
		return "redirect:/";
	}
	
	@GetMapping("/accessDenied")
	public void accessDecied() {
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "login/logout";
	}
	
}
