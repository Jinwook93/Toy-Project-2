//package com.toy.project.oauth2.token;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.toy.project.oauth2.GoogleOAuthService;
//import com.toy.project.oauth2.KakaoOAuthService;
//import com.toy.project.oauth2.NaverOAuthService;
//
//import lombok.RequiredArgsConstructor;
//
//
//@RestController
//public class KakaoOAuthController {
//
//    private final KakaoOAuthService kakaoOAuthService;
//
//    public KakaoOAuthController(KakaoOAuthService kakaoOAuthService) {
//        this.kakaoOAuthService = kakaoOAuthService;
//    }
//
//    @GetMapping("/login/oauth2/code/kakao")
//    public String kakaoCallback(@RequestParam(value = "code", required = false) String code,
//                                @RequestParam(value = "error", required = false) String error) {
//        if (error != null) {
//            return "카카오 로그인 실패: " + error;
//        }
//
//        System.out.println("Authorization Code: " + code);
//
//        // ✅ AccessToken 교환
//        var tokenResponse = kakaoOAuthService.getAccessToken(code);
//
//        String accessToken = (String) tokenResponse.get("access_token");
//        System.out.println("Access Token: " + accessToken);
//
//        return "Kakao AccessToken: " + accessToken;
//    }
//}