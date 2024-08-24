package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.example.demo.model.Categories;
import com.example.demo.model.Videos;

import lombok.Data;

@Data
public class CoursesDto {

	
	private long id;
	
	private String title;
	
	private String shortDesc;
	
	private String longDesc;
	
	private double price;
	private boolean live=false;
	private double discount;
	private Date createdDate;
	private String bannerName;
	private String contenttype;

	private List<Videos> videos;

	private List<Categories> categoryList;
}
