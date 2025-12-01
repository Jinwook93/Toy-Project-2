package com.toy.project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toy.project.dto.JoinDTO;
import com.toy.project.dto.UpdateUserDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.service.AdminService;
import com.toy.project.service.UserService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

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
//		private final AdminService adminService;
//		private final ObjectMapper objectMapper;
		
	@GetMapping("/isDuplicatedEmail")
	public Boolean checkEmail(String email) {
		 Boolean result = userService.duplicatedEmail(email);
		 return result? true : false;
	}
		
	@PostMapping("/join")
	public String postJoin(@RequestPart("joinDTO") JoinDTO joinDTO,  @RequestPart(name ="file", required = true) MultipartFile file) {
		System.out.println("파일이름"+file);
		Boolean result = userService.join(joinDTO,file);
		if(result) {
		return "join success";
		}else {
			return "join failed";
		}
	}			
	
	@PutMapping("/updateUser/{id}")
	public String putUpdateUser(@PathVariable(name = "id") Long id,@RequestPart("updateDTO") UpdateUserDTO updateDTO,  @RequestPart(name ="file", required = true) MultipartFile file) {
		System.out.println("파일이름"+file);
		Boolean result = userService.update(id,updateDTO,file);
		if(result) {
		return "update success";
		}else {
			return "update failed";
		}
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id) {
		Boolean result = userService.delete(id);
		if(result) {
		return "delete success";
		}else {
			return "delete failed";
		}
	}
	
//	@GetMapping("/allMember")
//	public String getAllMember() {
//		List<UserEntity> allMember = adminService.getAllMember();
//		String allMember_string = objectMapper.writeValueAsString(allMember);
//		return allMember_string;
//	}
	
}
