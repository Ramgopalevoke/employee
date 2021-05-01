package com.evoke.employee.service;

import java.util.List;
import com.evoke.employee.entity.Employee;

public interface EmployeeService {
    public List<Employee> getAllEmployeeDetails() throws Exception;

    Employee getEmployeeDetails(int id);

    public String deleteEmployeeDetails(int id);

    public String saveEmployeeDetails(Employee emp);

    public String updateEmployeeDetails(Employee emp);

}
