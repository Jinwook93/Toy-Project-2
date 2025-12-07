//package com.toy.project.oauth2.token;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.toy.project.oauth2.GoogleOAuthService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class GoogleOAuthController {
//	//엑세스토큰
//	private final GoogleOAuthService googleOAuthService;
//	
//	 // Redirect URI 엔드포인트
//   @GetMapping("/login/oauth2/code/google")
//   public String googleCallback(@RequestParam(value = "code", required = false) String code,
//           @RequestParam(value = "error", required = false) String error) {
//       // ✅ 구글이 전달한 Authorization Code 확인
//       System.out.println("Authorization Code: " + code);
//
//       // ✅ AccessToken 교환
//       var tokenResponse = googleOAuthService.getAccessToken(code);
//
//       // AccessToken 출력
//       String accessToken = (String) tokenResponse.get("access_token");
//       System.out.println("Access Token: " + accessToken);
//
//       return "AccessToken: " + accessToken;
//   }
//}
