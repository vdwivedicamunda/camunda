package com.example.workflow;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException{
	
	public EmployeeNotFoundException(String message) {

	    super(message);

	  }
	}


