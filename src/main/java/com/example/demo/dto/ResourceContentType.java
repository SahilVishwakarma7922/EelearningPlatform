package com.example.demo.dto;

import org.springframework.core.io.Resource;

import lombok.Data;

@Data
public class ResourceContentType {

	private Resource resource;
	private String contenttype;
}
