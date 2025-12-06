package com.toy.project.handler;


import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.toy.project.dto.CustomUserDetails;
import com.toy.project.entity.UserEntity;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    
    public OAuth2SuccessHandler(JwtUtil jwtUtil , UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {



        // OAuth2User에서 email 추출
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // DB에서 UserEntity 조회
        UserEntity userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // UserEntity 기반 Authentication 생성
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities() );

        // SecurityContextHolder에 덮어쓰기
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        
        
        
        
        
        

        String accesstoken = jwtUtil.createAccessToken(email, "USER", 7 * 24 * 60 * 60 * 1000L);
        String refreshToken = jwtUtil.createRefreshToken(email, 7 * 24 * 60 * 60 * 3000L);
        
        Cookie accesstoken_cookie = new Cookie("accessToken", accesstoken);
        accesstoken_cookie.setHttpOnly(true);
        accesstoken_cookie.setSecure(true);
        accesstoken_cookie.setMaxAge(60 * 30); // 30분
        accesstoken_cookie.setPath("/");			//(/ 이하 모든 요청)에 대해 쿠키가 전송됩니다

        
        Cookie refreshtoken_cookie = new Cookie("refreshToken", refreshToken);
        refreshtoken_cookie.setHttpOnly(true);
        refreshtoken_cookie.setSecure(true);
        refreshtoken_cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        refreshtoken_cookie.setPath("/");			//(/ 이하 모든 요청)에 대해 쿠키가 전송됩니다
        
        
        
        response.addCookie(accesstoken_cookie);
        response.addCookie(refreshtoken_cookie);
        
        
        // React 프론트엔드로 리다이렉트 (토큰 전달)
        response.sendRedirect("http://localhost:5173/oauth2/redirect");
    }
}