package com.toy.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toy.project.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Boolean  existsByEmail(String email);

	Optional<UserEntity> findByEmailAndProvider(String email, String provider);

	Boolean existsByNickname(String nickname);

	List<UserEntity>  findByEmail(String email);

//		@Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
//		List<UserEntity> findByEmails(@Param("email") String email);


}
