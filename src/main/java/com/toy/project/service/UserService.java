package com.toy.project.service;

import java.io.File;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.UpdateUserDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public Boolean duplicatedEmail(String email) {
		
		Boolean isDuplicated  = userRepository.existsByEmail(email);
		if(isDuplicated) {
			return true;
		}else {
			return false;
		}
		
	}
	
	@Transactional
	public Boolean join(JoinDTO joinDTO, MultipartFile file) {
		
		Boolean isDuplicated= this.duplicatedEmail(joinDTO.getEmail());
		if(isDuplicated) {
			return false;
		}else {
		joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));	//비밀번호 암호화
		UserEntity newuser = new UserEntity(joinDTO,file);
		userRepository.save(newuser);
		return true;
		}
	}
	
	@Transactional
	public Boolean update(Long id, UpdateUserDTO updateUserDTO, MultipartFile file) {
		
//		Boolean isDuplicated= this.duplicatedEmail(updateUserDTO.getEmail());
//		if(isDuplicated) {
//			return false;
//		}else {
		updateUserDTO.setPassword(bCryptPasswordEncoder.encode(updateUserDTO.getPassword()));	//비밀번호 암호화
		UserEntity updateduser = userRepository.findById(id).get();
		updateduser.updateEntity(updateUserDTO, file);
		userRepository.save(updateduser);
		return true;
//		}
	}

	
	//회원 삭제 
	public Boolean delete(Long id) {
		Boolean isPresent = userRepository.findById(id).isPresent();	
			
		if(isPresent) {
			userRepository.deleteById(id);
		}
		
		return isPresent;
	}
	
	
	
	
	
	
	
}
