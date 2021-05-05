package com.evoke.employee.dto;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.evoke.employee.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class EmployeeDTO {


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
    private String joiningDate;

    @JsonIgnore
    private Department department;

    @JsonIgnore
    private int id;

    @JsonIgnore
    private Date createdOn;
    @JsonIgnore
    private String createdBy;

    public EmployeeDTO() {
        super();
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

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


}
