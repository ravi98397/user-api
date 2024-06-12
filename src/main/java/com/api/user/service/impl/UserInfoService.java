package com.api.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.user.model.UserInfo;
import com.api.user.repository.UserInfoRepository;

@Service
public class UserInfoService {
	
	@Autowired
	private UserInfoRepository repo;
	
	public UserInfo addUserInfo(UserInfo info) {
		return repo.save(info);
	}
	
}
