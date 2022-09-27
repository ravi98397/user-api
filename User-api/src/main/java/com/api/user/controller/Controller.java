package com.api.user.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.model.Authority;
import com.api.user.model.UserInfo;
import com.api.user.model.UserLogin;
import com.api.user.service.impl.AuthorityService;
import com.api.user.service.impl.UserInfoService;
import com.api.user.service.impl.UserLoginService;

@RestController
public class Controller {
	
	@Autowired
	private UserLoginService loginService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping("/v1/csrf")
	public CsrfToken csrf(CsrfToken csrfToken) {
		return csrfToken;
	}
	
	@GetMapping("/hello")
	public String sayhello() {
		System.out.println("request accepted");
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return "hello " + name + "rama";
	}
	
	@GetMapping("/register")
	public String register() {
		//register admin
		System.out.println("running test");
		UserInfo info = new UserInfo();
		info.setFirstName("ravi");
		info.setLastName("Singh");
		//info = userInfoService.addUserInfo(info);
		
		
		Authority authority = new Authority();
		authority.setRoleCode("ADMIN");
		authority.setRoleDiscription("has all the access");
		authority = authorityService.addAuthority(authority);
		Set<Authority> authlist = new HashSet<>();
		authlist.add(authority);
		
		UserLogin user = new UserLogin("ravi", "singh", authlist);
		user.setUserInfo(info);
		user = loginService.adduser(user);
		return user.getUsername();
	}
}
