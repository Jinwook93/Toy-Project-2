package com.toy.project.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.project.entity.RefreshTokenEntity;
import com.toy.project.entity.UserEntity;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.repository.RefreshTokenRepository;
import com.toy.project.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequiredArgsConstructor
public class OAuth2Controller {
	
	
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	
	@PostMapping("/user/oAuth2login")
	public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
	    
		Cookie[] cookies = request.getCookies();
	    String accessToken = null;
	    String refreshToken = null;
	    
	    if (cookies != null) {			//쿠키검증
	        for (Cookie cookie : cookies) {
	            if ("accessToken".equals(cookie.getName())) {
	                accessToken = cookie.getValue();
	                break;
	            }
	        }
	    }

//	    if (cookies != null) {			//쿠키검증
//	        for (Cookie cookie : cookies) {
//	            if ("refreshToken".equals(cookie.getName())) {
//	                refreshToken = cookie.getValue();
//	                break;
//	            }
//	        }
//	    }
	    
	    
	    if (accessToken == null || !jwtUtil.validateJwt(accessToken)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    String email = jwtUtil.getEmail(accessToken);
	    UserEntity user = userRepository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));


	    
	    
	    //리프레시 토큰 조회
	    Optional<RefreshTokenEntity> optionalEntity = refreshTokenRepository.findByEmail(email);

	    RefreshTokenEntity refreshTokenEntity;

	    if (optionalEntity.isPresent()) {
	        // 이미 존재 → 토큰 갱신
	        refreshTokenEntity = optionalEntity.get();
	        refreshToken = jwtUtil.createRefreshToken(email, 7 * 24 * 60 * 60 * 1000L);
	        refreshTokenEntity.setToken(refreshToken);
	        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
	        refreshTokenRepository.save(refreshTokenEntity); // UPDATE 실행
	    } else {
	        // 새로 생성
	        refreshToken = jwtUtil.createRefreshToken(email, 7 * 24 * 60 * 60 * 1000L);
	        refreshTokenEntity = RefreshTokenEntity.builder()
	                .email(email)
	                .token(refreshToken)
	                .expiryDate(LocalDateTime.now().plusDays(7))
	                .deviceId("A")
	                .build();
	        refreshTokenRepository.save(refreshTokenEntity); // INSERT 실행
	    }

	    
	    Map<String, Object> userAndToken = new HashMap<>();
	    userAndToken.put("user", user);
	    userAndToken.put("accessToken", accessToken);
	    userAndToken.put("refreshToken", refreshToken);
	    return ResponseEntity.ok(userAndToken);
	}

}
