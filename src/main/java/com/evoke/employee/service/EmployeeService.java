package com.evoke.employee.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.evoke.employee.dto.EmployeeDTO;
import com.evoke.employee.entity.Employee;

public interface EmployeeService {
    public List<Employee> getAllEmployeeDetails() throws Exception;

	Employee getEmployeeDetails(int id) throws Exception;

	public String deleteEmployeeDetails(int id);

	public String saveEmployeeDetails(EmployeeDTO empDTO);

}
