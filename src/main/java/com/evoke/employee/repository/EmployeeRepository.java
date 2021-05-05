package com.evoke.employee.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.evoke.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);

    /*
     * @Query("SELECT d.depName, COUNT(d.depId) FROM Employee AS e, Dep GROUP BY d.name ORDER BY d.name DESC"
     * ) List<Object[]> countEmployeesForDepartment();
     */
}
