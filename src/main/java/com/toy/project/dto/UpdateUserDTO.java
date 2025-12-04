package com.toy.project.dto;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UpdateUserDTO {
	String email;
	String password;
	String password_check;

	String username;
	String nickname;

	String phone;
	String address;
	
//	String profile;
	//@Lob
    private byte[] profile;  // BLOB으로 매핑됨
	
	String role;
	
}
