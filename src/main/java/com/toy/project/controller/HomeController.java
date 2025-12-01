package com.toy.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.toy.project.dto.JoinDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HomeController {

	
	@GetMapping("/")
	public String getHome() {
		return "home";
	}
	
}
