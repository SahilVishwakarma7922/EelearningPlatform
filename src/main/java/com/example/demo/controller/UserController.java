package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AppConstants;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto userDto
//			BindingResult result
			){
//		if (result.hasErrors()) {
//			return new ResponseEntity<>("invalid data",HttpStatus.BAD_REQUEST);
//		}
		return new ResponseEntity<>(userService.saveUser(userDto),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id){
		return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(userService.findAllUser(),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id,@Valid @RequestBody UserDto dto){
		return new ResponseEntity<>(userService.updateUser(dto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id){
		return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/page")
	public ResponseEntity<?> findByPagination
	(
			@RequestParam(value = "pageNumber",required = false,defaultValue =AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
			@RequestParam(value = "pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "field")String field,
			@RequestParam(value = "direction")String direction
			
	)
	{
	CustomPageResponse userPagination = userService.findAllUserByPagination(pageNumber, pageSize,field,direction);	
		return new ResponseEntity<>(userPagination,HttpStatus.OK);
	}
}
