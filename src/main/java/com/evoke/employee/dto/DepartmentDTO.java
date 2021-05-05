package com.evoke.employee.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.evoke.employee.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DepartmentDTO {


    private int departmentId;
    @NotNull(message = "Please provide department name")
    @NotEmpty(message = "Please provide department name")
    @Size(max = 250, message = "department name should be less than 250 characters")
    private String departmentName;
    @NotNull(message = "Please provide description")
    @NotEmpty(message = "Please provide description")
    @Size(max = 250, message = "description should be less than 250 characters")
    private String description;


    public DepartmentDTO(int departmentId,
            @NotNull(message = "Please provide department name") @NotEmpty(message = "Please provide department name") @Size(max = 250,
                    message = "department name should be less than 250 characters") String departmentName,
            @NotNull(message = "Please provide description") @NotEmpty(message = "Please provide description") @Size(max = 250,
                    message = "description should be less than 250 characters") String description) {
        super();
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @JsonIgnore
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department UpdateDepDTO(Department dep, DepartmentDTO depDTo) {
        dep.setDepName(depDTo.getDepartmentName() != null && !"".equals(depDTo.getDepartmentName()
                .trim()) ? depDTo.getDepartmentName() : dep.getDepName());
        dep.setDescription(depDTo.getDescription() != null && !"".equals(depDTo.getDescription()
                .trim()) ? depDTo.getDescription() : dep.getDescription());
        return dep;
    }
}
