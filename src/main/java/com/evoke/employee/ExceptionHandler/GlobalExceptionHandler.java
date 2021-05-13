package com.evoke.employee.ExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> RecordNotFoundException(RecordNotFoundException ex, Locale locale) {
        List<String> errorList = new ArrayList<String>();
        errorList.add(ex.getMessage());
        log.error("Record Not Found:" + ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.toString(), errorList), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConstrainException.class)
    public ResponseEntity<ErrorResponse> dataConstrainException(DataConstrainException ex, Locale locale) {
        List<String> errorList = new ArrayList<String>();
        errorList.add(ex.getMessage());
        log.error("Data Integerity Exception:" + ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdFormatExeption.class)
    public ResponseEntity<ErrorResponse> InvalidEmployee(InvalidIdFormatExeption ex, Locale locale) {
        List<String> errorList = new ArrayList<String>();
        errorList.add(ex.getMessage());
        log.error("Invalid Input:" + ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> messageSource.getMessage(objectError, locale))
                .collect(Collectors.toList());
        log.error("Method Argument Not Valid:" + ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorMessages), HttpStatus.BAD_REQUEST);
    }

    /*
     * @ExceptionHandler(Exception.class) public ResponseEntity<ErrorResponse>
     * handleExceptions(Exception ex, Locale locale) { String errorMessage =
     * "Unexpected Error occured"; String error = HttpStatus.INTERNAL_SERVER_ERROR.toString(); if
     * (ex instanceof ConstraintViolationException) { error = HttpStatus.BAD_REQUEST.toString();
     * errorMessage = (((ConstraintViolationException) ex).getConstraintViolations()).iterator()
     * .next() .getMessage(); Logger.error("Constraint Violation:" + ex.getMessage(),
     * ex.getMessage()); } else { errorMessage = ex.getMessage(); ex.printStackTrace();
     * Logger.error("System Error:" + ex.getMessage(), ex.getMessage()); } List<String> errorList =
     * new ArrayList<String>(); errorList.add(errorMessage); return new ResponseEntity<>(new
     * ErrorResponse(error, errorList), HttpStatus.INTERNAL_SERVER_ERROR); }
     */

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass()
                    .getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ErrorResponse apiError = new ErrorResponse(ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
        res.sendError(ex.getHttpStatus()
                .value(), ex.getMessage());
    }

    // @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }
}
