package com.toy.project.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.toy.project.dto.JoinDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
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
	
	String profile;
	String role;

	@CreationTimestamp
	Date makingTime;
	
	@Builder
	public UserEntity(JoinDTO joinDTO){
		this.email = joinDTO.getEmail();
		this.username = joinDTO.getUsername();
		this.nickname = joinDTO.getNickname();
		this.phone = joinDTO.getPhone();
		this.address = joinDTO.getAddress();
		
		this.profile= joinDTO.getProfile();
		this.role = joinDTO.getRole().equals("USER")?"ROLE_USER":"ROLE_ADMIN";
	}
	
}
