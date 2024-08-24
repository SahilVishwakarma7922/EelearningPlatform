package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

@Component
public class UserMapper {

	
	@Autowired
	private Mapper mapper;
	
	public User toEntity(UserDto userDto) {
		return mapper.modelMapper().map(userDto, User.class);
	}
	
	public UserDto toDto(User user) {
		return mapper.modelMapper().map(user, UserDto.class);
	}
	
}
