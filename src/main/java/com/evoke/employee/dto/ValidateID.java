package com.evoke.employee.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.evoke.employee.ExceptionHandler.InvalidIdFormatExeption;


public class ValidateID {

    public ValidateID(String id) {
        this.id = id;
    }

    public ValidateID() {
        // TODO Auto-generated constructor stub
    }

    @NotNull(message = "Please provide id")
    @NotEmpty(message = "Please provide id")
    // @Pattern(regexp = "([a-zA-Z]+)", message = "employee id should be numeric")
    private String id;

    public int getId() throws Exception {
        if (id.matches("^[0-9]*$"))
            return Integer.valueOf(id);
        else
            throw new InvalidIdFormatExeption("id should be numeric");
    }

    public void setId(String id) {
        this.id = id;
    }

}
