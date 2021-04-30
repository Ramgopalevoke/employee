package com.evoke.employee.ExceptionHandler;

import java.util.List;

public class ErrorResponse {
    private String error;
    private List<String> messages;
   

    public ErrorResponse(String message,List<String> messages) {
        this.error = message;
        this.messages = messages;
    }
    
    public ErrorResponse(List<String> messages) {
        this.messages = messages;
    }

    public ErrorResponse(String message) {
        this.error = message;
    }

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
}