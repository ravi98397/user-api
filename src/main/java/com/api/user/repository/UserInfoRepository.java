package com.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.user.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String>{
}
