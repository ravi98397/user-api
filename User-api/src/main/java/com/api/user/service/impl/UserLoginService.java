package com.api.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.user.model.UserLogin;
import com.api.user.repository.UserLoginRepository;

@Service
public class UserLoginService implements UserDetailsService{
	
	@Autowired
	private UserLoginRepository repo;
	
	
	
	public UserLogin adduser(UserLogin user) {
		if(repo.findById(user.getUsername()).orElse(null) == null) {
			return repo.save(user);
		}else {
			return null;
		}
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLogin user = repo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("user not found with username = " + username);
		}
		return user;
	}
	

}
