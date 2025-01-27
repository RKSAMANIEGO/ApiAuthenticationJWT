package com.example.demo.controller.exceptionController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobasExpHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerValidationExeption(MethodArgumentNotValidException ex ){
		
		Map<String,String> resultError=new HashMap<>();
		
		ex.getBindingResult().getFieldErrors()
		.forEach(error -> resultError.put(error.getField() , error.getDefaultMessage()) );
		
	return new ResponseEntity<>(resultError,HttpStatus.BAD_REQUEST);	
	}
	
}
