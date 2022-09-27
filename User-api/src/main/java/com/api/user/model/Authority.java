package com.api.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = -3795317376769425040L;
	
	@Id
	private String roleCode;
	
	private String roleDiscription;
	
	@Override
	public String getAuthority() {
		return this.roleCode;
	}

	public Authority(String roleCode, String roleDiscription) {
		super();
		this.roleCode = roleCode;
		this.roleDiscription = roleDiscription;
	}

}
