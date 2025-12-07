package com.toy.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toy.project.dto.CustomUserDetails;
import com.toy.project.entity.UserEntity;
import com.toy.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String provider = null;
		UserEntity user = userRepository.findByEmailAndProvider(email, provider).orElseThrow(() -> new IllegalAccessError("로그인에 실패하였습니다"));
		return new CustomUserDetails(user);
	}
	
	
}
