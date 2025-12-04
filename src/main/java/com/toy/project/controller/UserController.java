package com.toy.project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.LoginDTO;
import com.toy.project.dto.ResponseDTO;
import com.toy.project.dto.UpdateUserDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.service.AdminService;
import com.toy.project.service.UserService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
		private final UserService userService;
		
	@GetMapping("/isDuplicatedEmail")
	public Boolean checkEmail(String email) {
		 Boolean result = userService.duplicatedEmail(email);
		 return result? true : false;
	}
		
	@PostMapping("/join")
	public  ResponseEntity<?>  postJoin(@RequestPart("joinDTO") JoinDTO joinDTO,  @RequestPart(name ="file", required = false) MultipartFile file) {
		Boolean result = userService.join(joinDTO,file);
		if(result) {
		return ResponseEntity.status(200).body("가입 성공");
		}else {
			return ResponseEntity.badRequest().body("가입 실패");
		}
//			return new ResponseDTO<>(200, "가입 성공");
//		}else {
//			return new ResponseDTO<>(400, "가입 실패");
//		}
			
	}			
	
	@PostMapping("/login")
	public ResponseEntity<?> postLogin(@RequestBody LoginDTO loginDTO) {
		String result = userService.login(loginDTO);
		String accessToken = result.split("===TOKEN BOUNDARY===")[0];
		String refreshToken = result.split("===TOKEN BOUNDARY===")[1];
		if (accessToken != null && !accessToken.isEmpty() &&
		        refreshToken != null && !refreshToken.isEmpty())  {
			return ResponseEntity.status(200)
					.header("Authorization", "Bearer " + accessToken) // 헤더에 토큰 추가
					 .header("Refresh-Token", refreshToken)
					.body(loginDTO.getEmail());
		}else {
			return ResponseEntity.badRequest().body("로그인 실패");
		}
	}	
	
	
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<?> putUpdateUser(@PathVariable(name = "id") Long id,@RequestPart("updateDTO") UpdateUserDTO updateDTO,  @RequestPart(name ="file", required = true) MultipartFile file) {
		System.out.println("파일이름"+file);
		Boolean result = userService.update(id,updateDTO,file);
		if(result) {
			return ResponseEntity.status(200).body("유저 정보 수정 성공");
		}else {
			return ResponseEntity.badRequest().body("유저 정보 수정 실패");
		}
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?>  deleteUser(@PathVariable(name = "id") Long id) {
		Boolean result = userService.delete(id);
		if(result) {
			return ResponseEntity.status(200).body("유저 정보 삭제 성공");
		}else {
			return ResponseEntity.badRequest().body("유저 정보 삭제 실패");
		}
	}
	

	
}
