package com.evoke.employee.dto;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DepartmentDTO {

    @JsonIgnore
    private int depId;
    @NotNull(message = "Please provide department name")
    @NotEmpty(message = "Please provide department name")
    @Size(max = 250, message = "department name should be less than 250 characters")
    private String depName;
    @NotNull(message = "Please provide description")
    @NotEmpty(message = "Please provide description")
    @Size(max = 250, message = "description should be less than 250 characters")
    private String description;
    @JsonIgnore
    private Date createdOn;
    @JsonIgnore
    private String createdBy;

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
