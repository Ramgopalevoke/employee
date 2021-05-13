package com.evoke.employee.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.security.JwtTokenProvider;
import com.evoke.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    DepartmentRepository depRepo;

    @Override
    public List<Employee> getAllEmployeeDetails() {
        return empRepo.findByOrderByIdAsc();
    }

    @Override
    public Employee getEmployeeDetails(int id) {
        return empRepo.findById(id)
                .orElse(null);
    }

    @Transactional
    public String deleteEmployeeDetails(Employee emp) {
        empRepo.delete(emp);
        return "employee.deleted";
    }

    @Transactional
    public String saveEmployeeDetails(Employee emp) {
        emp.setName(String.join(emp.getFirstName()
                .toUpperCase(), " ",
                emp.getLastName()
                        .toUpperCase()));
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        emp.setCreatedBy("System");
        emp.setCreatedOn(new Date());
        empRepo.save(emp);
        var map = new HashMap<>();
        map.put("email", emp.getEmail());
        return jwtTokenProvider.createToken(map);
    }

    @Transactional
    public String updateEmployeeDetails(Employee emp) {
        emp.setName(String.join(emp.getFirstName()
                .toUpperCase(), " ",
                emp.getLastName()
                        .toUpperCase()));
        emp.setUpdatedBy("System");
        emp.setUpdatedOn(new Date());
        empRepo.saveAndFlush(emp);
        return "employee.edit.success";
    }

    @Override
    public Employee employeeEmailCheck(String email) {
        return empRepo.findByEmail(email)
                .orElse(null);
    }


}
