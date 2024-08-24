package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.UserDto;

public interface UserService {

	public UserDto saveUser(UserDto dto);
	Optional<UserDto> findById(long id);
	public List<UserDto> findAllUser();
	public String updateUser(UserDto dto,long id);
	public String deleteUser(long id);
	public CustomPageResponse<UserDto> findAllUserByPagination(int pageNumber,int pageSize,String field,String direction);
}
