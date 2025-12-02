package com.toy.project.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.toy.project.entity.UserEntity;
import com.toy.project.repository.RefreshTokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
public class JwtUtil {

	
	private final SecretKey secretKey;
	
	public JwtUtil(@Value("${spring.jwt.secretKey}") String secret) {
	    secretKey = new SecretKeySpec(
	        secret.getBytes(StandardCharsets.UTF_8),
	        Jwts.SIG.HS256.key().build().getAlgorithm()
	    );
		
	}

	
	public String createAccessToken(String email, String role, Long expirationDate) {
		String token = Jwts.builder()
		.claim("email", email)
		.claim("role", role)			//ROLE_ 이 제거된 상태로 기입됨
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+ expirationDate))
		.signWith(secretKey)
         .compact();
		return token;
	}
	
	
	public String createRefreshToken(String email, Long expirationDate) {
		String token = Jwts.builder()
		.claim("email", email)
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+ expirationDate))
		.signWith(secretKey)
         .compact();
		return token;
	}
	
	public Boolean validateJwt(String token) {
        try {
            // 토큰 파싱 및 서명 검증
            Jwts.parser()
                .verifyWith(secretKey)   // 서명 키 설정
                .build()
                .parseSignedClaims(token); // JWS 토큰 검증 및 Claims 추출
            return true; // 예외 없으면 유효
        } catch (Exception e) {
            return false; // 서명 불일치, 만료, 포맷 오류 등
        }
    }


	public String getEmail(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
	}
	
	public String getRole(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	public Boolean getExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	
//	public void deleteRefreshToken(String token) {
//		if(this.getExpired(token)) {
//			RefreshTokenRepository.delete
//		}
//	}
	
	
	
}
