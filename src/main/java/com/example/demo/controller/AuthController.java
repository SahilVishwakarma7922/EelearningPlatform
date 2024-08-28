package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.jwtservice.JwtHelper;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtHelper helper;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public ResponseEntity<?> loggeInUser(@RequestBody LoginRequest loginRequest){
		
		UsernamePasswordAuthenticationToken authentication=
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		
		Authentication authenticate = authenticationManager.authenticate(authentication);
		UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
		User user2 = userRepository.findByEmail(loginRequest.getEmail()).get();
		String token = helper.generateToken(user);
		LoginResponse response=new LoginResponse();
		response.setEmail(user2.getEmail());
		response.setToken(token);
		response.setRoles(user2.getRoles());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
}
