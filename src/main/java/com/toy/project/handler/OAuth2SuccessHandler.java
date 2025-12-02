//package com.toy.project.handler;
//
//package com.toy.project.config;
//
//import java.io.IOException;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.toy.project.jwt.JwtUtil;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//    private final JwtUtil jwtUtil;
//
//    public OAuth2SuccessHandler(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//
//        // 인증된 사용자 이름(email 등)을 기반으로 JWT 발급
//        String username = authentication.getName();
//        String token = jwtUtil.generateToken(username);
//
//        // React 프론트엔드로 리다이렉트 (토큰 전달)
//        response.sendRedirect("http://localhost:3000/oauth2/redirect?token=" + token);
//    }
//}