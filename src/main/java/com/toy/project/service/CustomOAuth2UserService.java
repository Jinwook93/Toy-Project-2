package com.toy.project.service;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.toy.project.entity.UserEntity;
import com.toy.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	    OAuth2User oAuth2User = super.loadUser(userRequest);
	    String registrationId = userRequest.getClientRegistration().getRegistrationId(); 
	    //Spring Security에서 OAuth2UserRequest 안에는 어떤 provider(google, naver, kakao 등)로 
	    //로그인했는지 구분할 수 있는 registrationId 값이 들어 있습니다. 이걸로 분기하면 됩니다.

	    String email = null;
	    String name = null;
	    String provider = registrationId.toUpperCase();

	    if ("google".equals(registrationId)) {
	        // 구글은 email, name 속성이 바로 있음
	        email = oAuth2User.getAttribute("email");
	        name = oAuth2User.getAttribute("name");
	    } else if ("naver".equals(registrationId)) {
	        // 네이버는 response 안에 있음
	        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("response");
	        email = (String) response.get("email");
	        name = (String) response.get("name");
	    } else if ("kakao".equals(registrationId)) {
	        // 카카오는 kakao_account 안에 email 있음
	        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
	        email = (String) kakaoAccount.get("email");
	        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
	        name = (String) profile.get("nickname");
	    }

	    // DB 저장 or 기존 회원 조회
	    UserEntity userEntity = userRepository.findByEmailAndProvider(email, registrationId.toUpperCase())
	        .orElse(UserEntity.builder()
	            .email(email)
	            .password("temppassword")
	            .role("USER")
	            .provider(provider)
	            .build());

	    userRepository.save(userEntity);

	    return oAuth2User;
	}

  
}