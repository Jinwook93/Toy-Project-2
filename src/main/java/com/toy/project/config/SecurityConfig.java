package com.toy.project.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.toy.project.handler.OAuth2SuccessHandler;
import com.toy.project.jwt.JwtFilter;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.service.CustomOAuth2UserService;
import com.toy.project.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
	 // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    
    
 // ✅ Cors 설정 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // React 개발 서버
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Refresh-Token"));
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedOriginPattern("*"); // 개발 단계에서는 전체 허용

        // ✅ 응답 헤더 노출 허용
        configuration.setExposedHeaders(List.of("Authorization", "Refresh-Token"));

        
        configuration.setAllowCredentials(true); // 쿠키/인증정보 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    
    
	
    @Bean
//    @Order(1)
    SecurityFilterChain loginChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/user/login", "/user/join")
    	.formLogin(formLogin -> formLogin.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    
	
	@Bean
//	@Order(2)
	SecurityFilterChain apiChain(HttpSecurity http) {					//토큰을 비교하는 필터 수행
		http
		.securityMatcher("/**")
		.formLogin(formLogin -> formLogin.disable())
		.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.addFilterBefore(new JwtFilter(jwtUtil), BasicAuthenticationFilter.class)
		.authorizeHttpRequests((auth) ->
				auth.requestMatchers("/", "/user/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			.oauth2Login(oauth2 -> oauth2
//                .defaultSuccessUrl("/", true) // 로그인 성공 후 이동할 URL
//            );

			.oauth2Login(oauth -> oauth
//	            .loginPage("/login") // 커스텀 로그인 페이지(React에서 로그인 할 것이기 때문에 불필요)
	            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
	            .successHandler(oAuth2SuccessHandler) // JWT 발급 후 React로 redirect
	            );	
	        

		
		
		return http.build();
	}
}
