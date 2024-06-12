package com.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.user.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

}
