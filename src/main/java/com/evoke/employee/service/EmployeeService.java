package com.evoke.employee.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import com.evoke.employee.entity.Employee;

public interface EmployeeService {
    public List<Employee> getAllEmployeeDetails() throws Exception;

    Employee getEmployeeDetails(int id);

    Employee employeeEmailCheck(String email);

    public String deleteEmployeeDetails(Employee emp);

    public String saveEmployeeDetails(Employee emp) throws UnsupportedEncodingException, IOException, TimeoutException, IOException;

    public String updateEmployeeDetails(Employee emp);


}
