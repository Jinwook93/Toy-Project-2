package com.toy.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.toy.project.dto.JoinDTO;
import com.toy.project.entity.UserEntity;
import com.toy.project.service.AdminService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final ObjectMapper objectMapper;
	
	@GetMapping("/")
	public String getAdmin() {
		return "admin";
	}
	
	@GetMapping("/allMember")
	public String getAllMember() {
		List<UserEntity> allMember = adminService.getAllMember();
		String allMember_string = objectMapper.writeValueAsString(allMember);
		return allMember_string;
	}
	
}
