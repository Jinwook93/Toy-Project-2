package com.toy.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.toy.project.jwt.JwtFilter;
import com.toy.project.jwt.JwtUtil;
import com.toy.project.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;
	
	
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


	
    @Bean
    SecurityFilterChain loginChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/user/login", "/user/join")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    
	
	@Bean
	SecurityFilterChain apiChain(HttpSecurity http) {					//토큰을 비교하는 필터 수행
		http
		.securityMatcher("/**")
		.formLogin(formLogin -> formLogin.disable())
		.csrf(csrf -> csrf.disable())
		.addFilterBefore(new JwtFilter(jwtUtil), BasicAuthenticationFilter.class)
		.authorizeHttpRequests((auth) ->
				auth.requestMatchers("/", "/user/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		
		return http.build();
	}
}
