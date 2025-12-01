package com.toy.project.service;

import java.io.File;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.UpdateUserDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminService {

	private final UserRepository userRepository;
	
	
	
	public List<UserEntity> getAllMember(){
		return userRepository.findAll();
	}
	
	
	
	
	
}
