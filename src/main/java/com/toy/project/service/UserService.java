package com.toy.project.service;

import org.springframework.stereotype.Service;

import com.toy.project.dto.JoinDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public void join(JoinDTO joinDTO) {
		
		UserEntity newuser = new UserEntity(joinDTO);
		
	}
}
