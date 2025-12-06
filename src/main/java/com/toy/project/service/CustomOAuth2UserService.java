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
		 // 소셜에서 받은 사용자 정보
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        
        
        UserEntity userEntity = userRepository.findByEmail(email).
        	orElse(UserEntity.builder().email(email)
        			.password("temppassword")
        			.role("USER")							//임시로 USER로 만듬
        			.provider("GOOGLE")
        			.build());				

        System.out.println(email);
        System.out.println(name);
        
        userRepository.save(userEntity);
        
        
        
        
        
        // DB에 저장하거나 기존 회원과 매핑
        // 권한(Role) 부여 가능
        return oAuth2User;

	}

  
}