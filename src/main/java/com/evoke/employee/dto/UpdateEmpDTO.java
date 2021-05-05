package com.evoke.employee.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.evoke.employee.entity.Employee;


public class UpdateEmpDTO {

    public UpdateEmpDTO(@Size(max = 250, message = "Employee firstName should be less than 250 characters") String firstName,
            @Size(max = 250, message = "Employee lastName should be less than 250 characters") String lastName,
            @Size(max = 250, message = "Email should be less than 250 characters") @Email(message = "Please enter valid email address") String email, @Size(max = 13,
                    message = "Phone number should be less than 13 characters") @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid phone number") String phone) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public UpdateEmpDTO() {
        // TODO Auto-generated constructor stub
    }

    @Size(max = 250, message = "Employee firstName should be less than 250 characters")
    private String firstName;
    @Size(max = 250, message = "Employee lastName should be less than 250 characters")
    private String lastName;
    @Size(max = 250, message = "Email should be less than 250 characters")
    @Email(message = "Please enter valid email address")
    private String email;
    @Size(max = 13, message = "Phone number should be less than 13 characters")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid phone number")
    private String phone;
    @Pattern(regexp = "(^$|[0-9])", message = "Please enter valid department id")
    Integer depId;
    String dateOfJoining;

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

    public Employee UpdateEmpObject(Employee emp, UpdateEmpDTO empDTO) {
        emp.setEmail(empDTO.getEmail() != null && !"".equals(empDTO.getEmail()
                .trim()) ? empDTO.getEmail() : emp.getEmail());
        emp.setFirstName(empDTO.getFirstName() != null && !"".equals(empDTO.getFirstName()
                .trim()) ? empDTO.getFirstName() : emp.getFirstName());
        emp.setLastName(empDTO.getLastName() != null && !"".equals(empDTO.getLastName()
                .trim()) ? empDTO.getLastName() : emp.getLastName());
        emp.setPhone(empDTO.getPhone() != null && !"".equals(empDTO.getPhone()
                .trim()) ? empDTO.getPhone() : emp.getPhone());

        return emp;
    }


}
