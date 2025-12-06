package com.toy.project.entity;

import java.io.IOException;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.UpdateUserDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(
	    name = "users",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"email", "provider"})
	    }
	)

public class UserEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String email;
	String password;

	String username;
	String nickname;

	String phone;
	String address;
	
	//String profile;
    // 프로필 이미지를 DB에 직접 저장하려면
    @Lob
    private byte[] profile;  // BLOB으로 매핑됨

	
	String role;

	
	

	
	
	
	
	@CreationTimestamp
	Date makingTime;
	
	// 소셜 로그인일 경우 provider 정보 저장
  private String provider; // GOOGLE, NAVER, KAKAO 등

	
	//회원 임시 로그인 (Stateless)
	@Builder
	public UserEntity(String email, String password, String role,String provider){
		this.email = email;
		this.password = password;
		this.role = role;
		this.provider = provider;
	}
	
	

	
	
	
	
	
	
	
	
	
	//회원 등록
	@Builder
	public UserEntity(JoinDTO joinDTO,MultipartFile file){
		this.email = joinDTO.getEmail();
		this.password = joinDTO.getPassword();
		
		this.username = joinDTO.getUsername();
		this.nickname = joinDTO.getNickname();
		this.phone = joinDTO.getPhone();
		this.address = joinDTO.getAddress();
	    try {
	        this.profile = (file != null && !file.isEmpty()) ? file.getBytes() : null;
	    } catch (IOException e) {
	        throw new RuntimeException("파일 변환 실패", e);
	    }

	this.role = joinDTO.getRole().equals("ADMIN")?"ADMIN":"USER";


		
		
	}

	//회원 수정
	public void updateEntity(UpdateUserDTO updateUserDTO,MultipartFile file){
		this.email = updateUserDTO.getEmail();
		this.password = updateUserDTO.getPassword();
		
		this.username =  updateUserDTO.getUsername();
		this.nickname = updateUserDTO.getNickname();
		this.phone =  updateUserDTO.getPhone();
		this.address =  updateUserDTO.getAddress();
	    try {
	        this.profile = (file != null && !file.isEmpty()) ? file.getBytes() : null;
	    } catch (IOException e) {
	        throw new RuntimeException("파일 변환 실패", e);
	    }

	    this.role = updateUserDTO.getRole().equals("ADMIN")?"ADMIN":"USER";
	}
	
}
