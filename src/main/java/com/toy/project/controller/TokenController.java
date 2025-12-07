package com.toy.project.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.toy.project.entity.RefreshTokenEntity;
import com.toy.project.entity.UserEntity;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.repository.RefreshTokenRepository;
import com.toy.project.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController {
	
	private final RefreshTokenRepository refreshTokenRepository;	
	private final UserRepository userRepository;	
	private final JwtUtil jwtUtil;
	
	@PostMapping("/refresh")
	@Transactional
	public ResponseEntity<?> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
	    // DB에서 Refresh Token 조회
	    RefreshTokenEntity refreshTokenentity = refreshTokenRepository.findByToken(refreshToken)
	        .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

	    // 만료 여부 확인
	    if (refreshTokenentity.getExpiryDate().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token expired");
	    }

	    UserEntity loginUser = userRepository.findByEmailAndProvider(refreshTokenentity.getEmail(),refreshTokenentity.getProvider()).orElseThrow(() -> new IllegalArgumentException("유저를 조회할 수 없습니다"));  
	    String role = loginUser.getRole();
	    
	    // 새 Access Token 발급
	    String newAccessToken = jwtUtil.createAccessToken(refreshTokenentity.getEmail(),loginUser.getProvider() ,role , 15 * 60 * 1000L);

	    return ResponseEntity.ok()
	        .header("Authorization", "Bearer " + newAccessToken)
	        .body("Access Token 재발급 성공");
	}
}
