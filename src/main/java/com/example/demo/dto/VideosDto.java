package com.example.demo.dto;

import com.example.demo.model.Courses;

import lombok.Data;

@Data
public class VideosDto {
	
	private long videoId;
	
	private String title;
	
	private String description;
	private String filepath;
	private String contentType;
	private String size;
//	private long size1;
//	private Courses courses;
	
}
