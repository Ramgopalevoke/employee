package com.evoke.employee.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIdFormatExeption extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidIdFormatExeption(String exception) {
        super(exception);
    }
}
