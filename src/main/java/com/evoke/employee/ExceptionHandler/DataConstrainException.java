package com.evoke.employee.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataConstrainException extends RuntimeException {
    public DataConstrainException(String exception) {
        super(exception);
    }
}
