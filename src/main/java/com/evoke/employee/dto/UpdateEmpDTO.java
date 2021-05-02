package com.evoke.employee.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.evoke.employee.entity.Employee;


public class UpdateEmpDTO {

    public UpdateEmpDTO(@Size(max = 250, message = "Name should be less than 250 characters") String name,
            @Size(max = 250, message = "Email should be less than 250 characters") @Email(message = "Please enter valid email address") String email, @Size(max = 13,
                    message = "Phone number should be less than 13 characters") @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid phone number") String phone) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public UpdateEmpDTO() {
        // TODO Auto-generated constructor stub
    }

    @Size(max = 250, message = "Name should be less than 250 characters")
    private String name;
    @Size(max = 250, message = "Email should be less than 250 characters")
    @Email(message = "Please enter valid email address")
    private String email;
    @Size(max = 13, message = "Phone number should be less than 13 characters")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid phone number")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public Employee UpdateEmpObject(Employee emp, UpdateEmpDTO empDTO) {
        emp.setEmail(empDTO.getEmail() != null && !"".equals(empDTO.getEmail()
                .trim()) ? empDTO.getEmail() : emp.getEmail());
        emp.setName(empDTO.getName() != null && !"".equals(empDTO.getName()
                .trim()) ? empDTO.getName() : emp.getName());
        emp.setPhone(empDTO.getPhone() != null && !"".equals(empDTO.getPhone()
                .trim()) ? empDTO.getPhone() : emp.getPhone());

        return emp;
    }


}
