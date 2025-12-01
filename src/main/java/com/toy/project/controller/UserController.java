package com.toy.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.toy.project.dto.JoinDTO;
import com.toy.project.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class UserController {
	
		private final UserService userService;

		
	@GetMapping("/isDuplicatedEmail")
	public Boolean checkEmail(String email) {
		 Boolean result = userService.duplicatedEmail(email);
		 return result? true : false;
	}
		
	@PostMapping("/join")
	public String postJoin(JoinDTO joinDTO) {
		Boolean result = userService.join(joinDTO);
		if(result) {
		return "join success";
		}else {
			return "join failed";
		}
	}
}
