package com.example.demo.dto;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	
	private long userid;
	private String name;
	
	@Email(message = "email is not valid ")
	private String email;
	@NotEmpty
	@Size(min = 10,max = 10,message = "phone  number should be 10 digits only")
	private String phoneNumber;	
	private String password;
	private String about;
	private boolean active;
	private boolean emailVerified;
	private Date createdAt;
	private String profilePath;
	private String recentOtp;
	
}
