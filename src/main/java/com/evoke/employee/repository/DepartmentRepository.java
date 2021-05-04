package com.evoke.employee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.evoke.employee.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {


    @Query("SELECT d.name, COUNT(d.id) FROM Department AS d GROUP BY d.name ORDER BY d.name DESC")
    List<Object[]> countEmployeesForDepartment();
}
