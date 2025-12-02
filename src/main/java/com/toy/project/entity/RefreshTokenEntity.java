package com.toy.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class RefreshTokenEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email; // 사용자 식별
	private String token; // Refresh Token 값
	private LocalDateTime expiryDate; // 만료일

	private String deviceId;     // 기기 식별자 (예: UUID, userAgent)

}
