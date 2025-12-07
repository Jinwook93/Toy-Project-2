//package com.toy.project.oauth2.token;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.toy.project.oauth2.GoogleOAuthService;
//import com.toy.project.oauth2.NaverOAuthService;
//
//import lombok.RequiredArgsConstructor;
//
//
//@RestController
//public class NaverOAuthController {
//
//    private final NaverOAuthService naverOAuthService;
//
//    public NaverOAuthController(NaverOAuthService naverOAuthService) {
//        this.naverOAuthService = naverOAuthService;
//    }
//
//    @GetMapping("/login/oauth2/code/naver")
//    public String naverCallback(@RequestParam(value = "code", required = false) String code,
//                                @RequestParam(value = "state", required = false) String state,
//                                @RequestParam(value = "error", required = false) String error) {
//        if (error != null) {
//            return "네이버 로그인 실패: " + error;
//        }
//
//        System.out.println("Authorization Code: " + code);
//
//        // ✅ AccessToken 교환
//        var tokenResponse = naverOAuthService.getAccessToken(code, state);
//
//        String accessToken = (String) tokenResponse.get("access_token");
//        System.out.println("Access Token: " + accessToken);
//
//        return "Naver AccessToken: " + accessToken;
//    }
//}