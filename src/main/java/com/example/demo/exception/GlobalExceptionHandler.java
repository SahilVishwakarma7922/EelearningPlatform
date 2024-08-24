package com.example.demo.exception;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
		CustomMessage msg = new CustomMessage();
		msg.setMessage(ex.getMessage());
		msg.setSuccess(false);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex){
		Map<String, String> errors=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldName=((FieldError)error).getField();
			String errorMessage=error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		return new ResponseEntity<Map<String,String>>(errors,HttpStatus.OK);
	}
	
}
