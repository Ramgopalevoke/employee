package com.evoke.employee.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    @Override
    public List<Employee> getAllEmployeeDetails() {
        return empRepo.findAll();
    }

    @Override
    public Employee getEmployeeDetails(int id) {
        return empRepo.findById(id)
                .orElse(null);
    }

    @Transactional
    public String deleteEmployeeDetails(int id) {
        empRepo.deleteById(id);
        return "employee.deleted";
    }

    @Transactional
    public String saveEmployeeDetails(Employee emp) {
        emp.setCreatedBy("System");
        emp.setCreatedOn(new Date());
        empRepo.save(emp);
        return "employee.save.success";
    }

    @Transactional
    public String updateEmployeeDetails(Employee emp) {
        emp.setUpdatedBy("System");
        emp.setUpdatedOn(new Date());
        empRepo.saveAndFlush(emp);
        return "employee.edit.success";
    }

}
