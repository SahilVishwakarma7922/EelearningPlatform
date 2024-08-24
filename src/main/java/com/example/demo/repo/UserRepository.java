package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public boolean existsByEmail(String name);
	public boolean existsByPhoneNumber(String phonenumber);
	
//	Optional<User> findByActive(String active);

	Optional<User> findByEmail(String email);
	Optional<User> findByPhoneNumber(String phoneNumber);
	
}
