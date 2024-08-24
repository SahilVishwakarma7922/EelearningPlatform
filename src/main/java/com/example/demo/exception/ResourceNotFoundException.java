package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {
		super("Resource Not Found Exception");
	}
	
	
	public ResourceNotFoundException(String courseNotFound) {
		super(courseNotFound);
	}
	
}
