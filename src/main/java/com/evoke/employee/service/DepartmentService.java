package com.evoke.employee.service;

import java.util.List;
import com.evoke.employee.entity.Department;

public interface DepartmentService {

    Department getDepartmentDetails(int depId);

    public String deleteDepartment(Department dep) throws Exception;

    public String saveDepartment(Department dep);

    public String updateDepartment(Department dep);

    List<Department> getAllDepartments() throws Exception;

    Department departmentByName(String departmentName);

}
