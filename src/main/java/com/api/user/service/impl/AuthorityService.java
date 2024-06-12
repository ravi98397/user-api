package com.api.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.user.model.Authority;
import com.api.user.repository.AuthorityRepository;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository repo;
	
	public Authority addAuthority(Authority auth) {
		return repo.save(auth);
	}
	
}
