package com.toy.project.service;

import java.io.File;
import java.time.LocalDateTime;
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
import com.toy.project.dto.UserInfoDTO;
import com.toy.project.entity.RefreshTokenEntity;
import com.toy.project.entity.UserEntity;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.repository.RefreshTokenRepository;
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
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public Boolean duplicatedEmail(String email) {
	    return userRepository.existsByEmail(email);
	}


	@Transactional
	public Boolean join(JoinDTO joinDTO, MultipartFile file) {
		System.out.println("여기까지?");
		Boolean isDuplicated = this.duplicatedEmail(joinDTO.getEmail());
		if (isDuplicated) {
			return false;
		} else {
			joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword())); // 비밀번호 암호화
			
			UserEntity newuser = new UserEntity(joinDTO, file);
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
		updateUserDTO.setPassword(bCryptPasswordEncoder.encode(updateUserDTO.getPassword())); // 비밀번호 암호화
		UserEntity updateduser = userRepository.findById(id).get();
		updateduser.updateEntity(updateUserDTO, file);
		userRepository.save(updateduser);
		return true;
//		}
	}

	// 회원 삭제
	@Transactional
	public Boolean delete(Long id) {
		Boolean isPresent = userRepository.findById(id).isPresent();

		if (isPresent) {
			userRepository.deleteById(id);
		}

		return isPresent;
	}

	// 로그인
	@Transactional
	public String login(LoginDTO loginDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 권한(Role) 추출
		String role = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).findFirst() // 여러 권한이 있을 경우 첫 번째만 사용
				.orElse("ROLE_USER");

		// ROLE_ 제거 후 JWT에 저장
		if (role.startsWith("ROLE_")) {
			role = role.substring(5); // "ROLE_ADMIN" -> "ADMIN"
		}

		Optional<RefreshTokenEntity> searchRefreshTokenEntity = refreshTokenRepository.findByEmail(loginDTO.getEmail());

		Boolean IsDuplicatedRefreshToken = searchRefreshTokenEntity.isPresent();
		String refreshToken = "";

		if (!IsDuplicatedRefreshToken) { // 중복된 refreshToken이 없다면 토큰생성
			// 리프레시 토큰 생성 및 저장
			refreshToken = jwtUtil.createRefreshToken(loginDTO.getEmail(), 7 * 24 * 60 * 60 * 1000L);

			RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().email(loginDTO.getEmail())
					.token(refreshToken).expiryDate(LocalDateTime.now().plusDays(7)).deviceId("A").build();
			refreshTokenRepository.save(refreshTokenEntity);
		} else {
			refreshToken = searchRefreshTokenEntity.get().getToken();

			// refreshToken이 있어도 만료된 경우 삭제
			Boolean IsExpiredToken = jwtUtil.getExpired(refreshToken);
			if (IsExpiredToken) {
				refreshTokenRepository.deleteByEmail(loginDTO.getEmail());
				// 리프레시 토큰 생성 및 저장
				refreshToken = jwtUtil.createRefreshToken(loginDTO.getEmail(), 7 * 24 * 60 * 60 * 1000L);

				RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().email(loginDTO.getEmail())
						.token(refreshToken).expiryDate(LocalDateTime.now().plusDays(7)).deviceId("A").build();
				refreshTokenRepository.save(refreshTokenEntity);
			}

		}

		String accessToken = jwtUtil.createAccessToken(loginDTO.getEmail(), role, 15 * 60 * 1000L);

		return accessToken + "===TOKEN BOUNDARY===" + refreshToken;
	}


//@Pathvariable 사용하여 id 식별번호를 비교
//	@Transactional
//	public UserInfoDTO getLoginUserInfo(Long id,String token) {
//			String role = jwtUtil.getRole(token);
//			String email = jwtUtil.getEmail(token);
//			Optional<UserEntity> userEntity =userRepository.findByEmail(email);
//			
//			
//			if(!userEntity.isPresent()) {
//				return null;
//			}
//			UserInfoDTO userInfoDTO = new UserInfoDTO(); //로그인한 유저
//			if(id != userEntity.get().getId()) {
//				System.out.println("조회한 식별번호와 토큰 정보와 같지 않음");
//				return null;
//			}
//			
//			
//			System.out.println("ROLE_ADMIN::: "+ jwtUtil.getRole(token));
//			userInfoDTO.UserEntityToUserInfoDTO(userEntity);
//			  // ADMIN은 모든 유저 조회 가능
//		    if ("ADMIN".equals(role)) {
//		        return userInfoDTO;
//		    }
//
//		    // USER는 토큰의 이메일과 DB의 이메일이 같을 때만 조회 가능
//		    if ("USER".equals(role) && userEntity.get().getEmail().equals(email)) {
//		        return userInfoDTO;
//		    }
//			return null;
//
//			
//
//	}
	
	
	//@Pathvariable 없이 token으로 값 확인
	@Transactional
	public UserInfoDTO getLoginUserInfo(String token) {
			String role = jwtUtil.getRole(token);
			String email = jwtUtil.getEmail(token);
			Optional<UserEntity> userEntity =userRepository.findByEmail(email);
			
		
			if(!userEntity.isPresent()) {
				return null;
			}
			UserInfoDTO userInfoDTO = new UserInfoDTO(); //로그인한 유저
//			if(id != userEntity.get().getId()) {
//				System.out.println("조회한 식별번호와 토큰 정보와 같지 않음");
//				return null;
//			}
			
			
			System.out.println("ROLE_ADMIN::: "+ jwtUtil.getRole(token));
			userInfoDTO.UserEntityToUserInfoDTO(userEntity);
			  // ADMIN은 모든 유저 조회 가능
		    if ("ADMIN".equals(role)) {
		        return userInfoDTO;
		    }

		    // USER는 토큰의 이메일과 DB의 이메일이 같을 때만 조회 가능
		    if ("USER".equals(role) && userEntity.get().getEmail().equals(email)) {
		        return userInfoDTO;
		    }
			return null;

			

	}
	
	
	
	//로그아웃
	
//	@Transactional
//	public Boolean logout(Long id, String token) {
//	    UserEntity userEntity = userRepository.findById(id).orElseThrow();
//	    if(userEntity.getEmail().equals(jwtUtil.getEmail(token))) {
//	    	refreshTokenRepository.deleteByEmail(email); //로그아웃 시 리프레시 토큰 삭제
//	    	return true;
//	    }else {
//	    	return false;
//	    }
//	}

	@Transactional
	public Boolean logout(String token) {
			String email = jwtUtil.getEmail(token.replace("Bearer ", ""));
	    	refreshTokenRepository.deleteByEmail(email); //로그아웃 시 리프레시 토큰 삭제
	    	return true;
	}

	
	
	
	
}
