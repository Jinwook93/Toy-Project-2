package com.toy.project.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

	
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

	
	
	
	
//	@Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String clientId;
//	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String clientSecret;
//	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
//    private String redirectUri;
	
    public Map<String, Object> getAccessToken(String authorizationCode) {
        String url = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 파라미터
        String body = "code=" + authorizationCode
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectUri
                + "&grant_type=authorization_code";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                Map.class
        );

        // 응답 JSON: access_token, refresh_token, expires_in, scope, token_type
        return response.getBody();
    }
}
