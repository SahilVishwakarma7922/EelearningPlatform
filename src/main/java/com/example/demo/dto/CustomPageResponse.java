package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomPageResponse<T> {

	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
	private List<T> content;
	
}
