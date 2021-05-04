package com.evoke.employee.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository depRepo;

    @Override
    public List<Object[]> getCountOfEmployeeForDepartment() throws Exception {
        // TODO Auto-generated method stub
        return depRepo.countEmployeesForDepartment();
    }

}
