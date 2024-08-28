package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Role;

import lombok.Data;

@Data
public class LoginResponse {

	private String email;
	private String token;
	private List<Role> roles;
}
