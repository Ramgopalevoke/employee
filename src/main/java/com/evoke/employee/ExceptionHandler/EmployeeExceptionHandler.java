package com.evoke.employee.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {

    private final Logger Logger = LoggerFactory.getLogger(this.getClass());

    private final MessageSource messageSource;

    @Autowired
    public EmployeeExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> RecordNotFoundException(RecordNotFoundException ex, Locale locale) {
        List<String> errorList = new ArrayList<String>();
        errorList.add(ex.getMessage());
        Logger.error("Record Not Found:" + ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.toString(), errorList), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidIdFormatExeption.class)
    public ResponseEntity<ErrorResponse> InvalidEmployee(InvalidIdFormatExeption ex, Locale locale) {
        List<String> errorList = new ArrayList<String>();
        errorList.add(ex.getMessage());
        Logger.error("Invalid Input:", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> messageSource.getMessage(objectError, locale))
                .collect(Collectors.toList());
        Logger.error("Method Argument Not Valid:", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex, Locale locale) {
        String errorMessage = "Unexpected Error occured";
        String error = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        if (ex instanceof ConstraintViolationException) {
            error = HttpStatus.BAD_REQUEST.toString();
            errorMessage = (((ConstraintViolationException) ex).getConstraintViolations()).iterator()
                    .next()
                    .getMessage();
            Logger.error("Constraint Violation:", ex.getMessage());

        } else {
            errorMessage = ex.getMessage();
            Logger.error("System Error:", ex.getMessage());
        }
        ex.printStackTrace();
        List<String> errorList = new ArrayList<String>();
        errorList.add(errorMessage);
        return new ResponseEntity<>(new ErrorResponse(error, errorList), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
