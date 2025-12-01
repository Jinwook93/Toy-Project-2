package com.toy.project.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toy.project.dto.CustomUserDetails;
import com.toy.project.entity.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		String authorization = request.getHeader("Authorization");
		String token ="";
		if(authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
		}
		
		token = authorization.split(" ")[1];
		
		Boolean tokenResult = jwtUtil.validateJwt(token);		//토큰 검증
		if(tokenResult) {
	          System.out.println("token success");
	          
	          
	        //토큰에서 username과 role 획득
	          String username = jwtUtil.getEmail(token);
	          String role = jwtUtil.getRole(token);
	  		Boolean expired = jwtUtil.getExpired(token);
	  		
//	  		토큰 소멸 시간 검증
	        if (jwtUtil.getExpired(token)) {

	            System.out.println("token expired");
	            filterChain.doFilter(request, response);

				//조건이 해당되면 메소드 종료 (필수)
	            return;
	        }
	  		
	  		
	  		//userEntity를 생성하여 값 set
	          UserEntity userEntity = new UserEntity(username,"temppassword",role);
	
	  		//UserDetails에 회원 정보 객체 담기
	          CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

	  		//스프링 시큐리티 인증 토큰 생성
	          Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
	  		//세션에 사용자 등록
	          SecurityContextHolder.getContext().setAuthentication(authToken);
	            filterChain.doFilter(request, response);
	            
		}else {
			System.out.println("token fail");
	        filterChain.doFilter(request, response);
			return;
		}
		
		
	}

}
