package com.evoke.employee.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class SaveEmployeeDTO {


    @NotNull(message = "Please provide employee first name")
    @NotEmpty(message = "Please provide employee first name")
    @Size(max = 250, message = "Name should be less than 250 characters")
    private String firstName;
    @NotNull(message = "Please provide employee last name")
    @NotEmpty(message = "Please provide employee last name")
    @Size(max = 250, message = "Name should be less than 250 characters")
    private String lastName;
    @NotNull(message = "Please provide employee email")
    @NotEmpty(message = "Please provide employee email")
    @Size(max = 250, message = "Email should be less than 250 characters")
    @Email(message = "Please enter valid email address")
    private String email;
    @NotNull(message = "Please provide employee phone")
    @NotEmpty(message = "Please provide employee phone")
    @Size(max = 13, message = "Phone number should be less than 13 characters")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid phone number")
    private String phone;
    @NotNull(message = "Please provide department id")
    // @Pattern(regexp = "(^$|[0-9])", message = "Please enter valid department id")
    private Integer depId;
    @NotNull(message = "Please provide date of joining")
    @NotEmpty(message = "Please provide date of joining")
    private String dateOfJoining;


    public SaveEmployeeDTO(
            @NotNull(message = "Please provide employee first name") @NotEmpty(message = "Please provide employee first name") @Size(max = 250,
                    message = "Name should be less than 250 characters") String firstName,
            @NotNull(message = "Please provide employee last name") @NotEmpty(message = "Please provide employee last name") @Size(max = 250,
                    message = "Name should be less than 250 characters") String lastName,
            @NotNull(message = "Please provide employee email") @NotEmpty(message = "Please provide employee email") @Size(max = 250,
                    message = "Email should be less than 250 characters") @Email(message = "Please enter valid email address") String email,
            @NotNull(message = "Please provide employee phone") @NotEmpty(message = "Please provide employee phone") @Size(max = 13,
                    message = "Phone number should be less than 13 characters") @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid phone number") String phone,
            @NotNull(message = "Please provide department id") @Pattern(regexp = "(^$|[0-9])", message = "Please enter valid department id") Integer depId,
            @NotNull(message = "Please provide date of joining") @NotEmpty(message = "Please provide date of joining") String dateOfJoining) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.depId = depId;
        this.dateOfJoining = dateOfJoining;
    }

    public SaveEmployeeDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }


}
