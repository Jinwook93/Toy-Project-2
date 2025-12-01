package com.toy.project.dto;

import lombok.Data;

@Data
public class JoinDTO {
	String email;
	String password;
	String password_check;

	String username;
	String nickname;

	String phone;
	String address;
	
	String profile;
	String role;
	
}
