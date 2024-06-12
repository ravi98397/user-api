package com.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.user.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, String> {
	public UserLogin findByUsername(String username);
}
