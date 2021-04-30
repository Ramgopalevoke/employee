package com.evoke.employee.dto;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class ValidateEmployeeID {
	
	public ValidateEmployeeID(String id) {
		this.id = id;
	}
	@NotNull(message = "Please provide valid employee id") 
	@NotEmpty(message = "Please provide employee id")
	//@Pattern(regexp = "([a-zA-Z]+)", message = "employee id should be numeric")
	private String id;
    
	public int getId() throws Exception {
		if(id.matches("^[0-9]*$"))
		   return Integer.valueOf(id);
		else
		   throw new Exception("employee id should be numeric");
		
	}
	public void setId(String id) {
		this.id = id;
	}
    
}
