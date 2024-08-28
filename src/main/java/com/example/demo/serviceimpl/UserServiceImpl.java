package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto saveUser(UserDto dto) {
		
		if (userRepo.existsByEmail(dto.getEmail())) {
			throw new ResourceNotFoundException("This email id is already exists please choose other one ");
		}
		
		User entity = mapper.toEntity(dto);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		java.util.Date date = new java.util.Date();
		entity.setCreatedAt(date);
		User save = userRepo.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public Optional<UserDto> findById(long id) {
		userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not present with id " + id));

		Optional<User> user1 = userRepo.findById(id);
		Optional<UserDto> map = user1.map(use -> mapper.toDto(use));
		return map;

	}

	@Override
	public List<UserDto> findAllUser() {

		List<User> user = userRepo.findAll();
		List<UserDto> list = user.stream().map(course -> mapper.toDto(course)).toList();
		return list;
	}

	@Override
	public String updateUser(UserDto dto, long id) {

		userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not present with id " + id));
		User user = mapper.toEntity(dto);
		user.setUserid(id);
		userRepo.save(user);
		return " succesfully updated the user with id " + id;

	}

	@Override
	public String deleteUser(long id) {

		userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found !!!"));
		userRepo.deleteById(id);
		return "deleted succesfully";
	}

	@Override
	public CustomPageResponse<UserDto> findAllUserByPagination(int pageNumber, int pageSize, String field,
			String direction) {

		Sort sorted = null;
		if (direction.equals("desc")) {
			sorted = Sort.by(Sort.Direction.DESC, field);
		} else if (direction.equals("asc")) {
			sorted = Sort.by(Sort.Direction.ASC, field);
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sorted);

		Page<User> user = userRepo.findAll(pageable);
		List<User> allUser = user.getContent();
		List<UserDto> users = allUser.stream().map(us -> mapper.toDto(us)).toList();

		CustomPageResponse<UserDto> customPageResponse = new CustomPageResponse<>();
		customPageResponse.setPageNumber(pageNumber);
		customPageResponse.setPageSize(pageSize);
		customPageResponse.setContent(users);
		customPageResponse.setTotalPages(user.getTotalPages());
		customPageResponse.setLast(user.isLast());

		customPageResponse.setTotalElements(user.getTotalElements());

		return customPageResponse;
	}

}
