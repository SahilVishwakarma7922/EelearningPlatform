package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.example.demo.model.Courses;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriesDto {
	
	private long id;
	
	@NotEmpty(message = "title is required please fill it")
	@Size(min = 3,max = 50,message = "title must be between 3 and 50 characters")
	private String title;
	
	@NotEmpty(message = "description is required")
	private String description;
	
	private Date addedDate;

	private List<Courses> courses;
}
