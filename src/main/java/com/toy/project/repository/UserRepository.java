package com.toy.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.project.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Boolean  existsByEmail(String email);

	Optional<UserEntity> findByEmailAndProvider(String email, String provider);

	Boolean existsByNickname(String nickname);

		Optional<UserEntity> findByEmail(String email);

}
