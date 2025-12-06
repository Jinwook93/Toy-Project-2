package com.toy.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.project.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

	Optional<RefreshTokenEntity> findByToken(String refreshToken);
	Optional<RefreshTokenEntity> findByEmailAndProvider(String email, String provider);
	void deleteByToken(String refreshToken);
	void deleteByEmailAndProvider(String email, String provider);

}
