package com.toy.project.service;

import java.io.File;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.LoginDTO;
import com.toy.project.dto.UpdateUserDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
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

	//로그인
	public String login(LoginDTO loginDTO) {
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
	    );
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    
	    // 권한(Role) 추출
	    String role = authentication.getAuthorities().stream()
	        .map(GrantedAuthority::getAuthority)
	        .findFirst()   // 여러 권한이 있을 경우 첫 번째만 사용
	        .orElse("ROLE_USER");
	    
	 // ROLE_ 제거 후 JWT에 저장
	    if (role.startsWith("ROLE_")) {
	        role = role.substring(5); // "ROLE_ADMIN" -> "ADMIN"
	    }

	    
	    
	    return jwtUtil.createToken(loginDTO.getEmail(),role , 60*60*1000L);
	}
	
	
	
	
	
	
}
