package com.toy.project.dto;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.UpdateUserDTO;
import com.toy.project.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDTO {

	
	Long id;
	
	String email;
	String password;

	String username;
	String nickname;

	String phone;
	String address;
	

   // @Lob
    private byte[] profile;  

	String role;

	Date makingTime;
	
	// 소셜 로그인일 경우 provider 정보 저장
//    private String provider; // GOOGLE, NAVER, KAKAO 등

	
	//회원 임시 로그인 (Stateless)
	public UserInfoDTO(String email, String password, String role){
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	//회원 정보 조회
	public void UserEntityToUserInfoDTO (Optional<UserEntity> userEntity){
		this.id = userEntity.get().getId();
		this.email = userEntity.get().getEmail();
		this.password = userEntity.get().getPassword();
		this.role = userEntity.get().getRole();
		this.address = userEntity.get().getAddress();
		this.phone = userEntity.get().getPhone();
		this.profile = userEntity.get().getProfile();
		this.username = userEntity.get().getUsername();
		this.nickname = userEntity.get().getNickname();
		this.makingTime = userEntity.get().getMakingTime();
	}
	
	
	
}
