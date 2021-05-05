package com.evoke.employee.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.evoke.employee.ExceptionHandler.DataConstrainException;
import com.evoke.employee.entity.Department;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository depRepo;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Override
    public List<Department> getAllDepartments() throws Exception {
        // TODO Auto-generated method stub
        return depRepo.findAll();
    }

    @Override
    public Department getDepartmentDetails(int id) {
        return depRepo.findById(id)
                .orElse(null);
    }

    @Override
    public String deleteDepartment(Department dep) throws Exception {
        try {
            depRepo.delete(dep);
        } catch (DataIntegrityViolationException e) {
            throw new DataConstrainException(messageSource.getMessage("department.constrain.violation", new Object[] {dep.getDepId()}, null));
        }
        return "department.deleted";
    }

    @Override
    public String saveDepartment(Department dep) {
        dep.setCreatedBy("System");
        dep.setCreatedOn(new Date());
        depRepo.save(dep);
        return "department.save.success";
    }

    @Override
    public String updateDepartment(Department dep) {
        dep.setUpdatedBy("System");
        dep.setUpdatedOn(new Date());
        depRepo.save(dep);
        return "department.edit.success";
    }

    @Override
    public Department departmentByName(String departmentName) {
        return depRepo.findByDepName(departmentName)
                .orElse(null);
    }

}
