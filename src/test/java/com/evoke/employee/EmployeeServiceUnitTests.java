package com.evoke.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.security.JwtTokenProvider;
import com.evoke.employee.service.impl.DepartmentServiceImpl;
import com.evoke.employee.service.impl.EmployeeServiceImpl;

@SpringBootTest
class EmployeeServiceUnitTests {

    @Autowired
    EmployeeServiceImpl empServce;

    @Autowired
    DepartmentServiceImpl depServce;

    @Autowired
    EmployeeRepository empDao;

    @Autowired
    DepartmentRepository depDao;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Employee empConstructor(String firstName, String lastName, String email, String phone, String doj, int depId, String password) {
        Employee empDto = new Employee();
        Department dep = depDao.findAll()
                .get(0);
        empDto.setFirstName(firstName);
        empDto.setLastName(lastName);
        empDto.setEmail(email);
        empDto.setPhone(phone);
        empDto.setJoiningDate(doj);
        empDto.setDepId(dep.getDepId());
        empDto.setDepartment(dep);
        empDto.setPassword(password);
        return empDto;
    }

    @Test
    @Order(1)
    public void getAllEmployeesTest() throws Exception {
        assertEquals(empDao.findAll()
                .size(),
                empServce.getAllEmployeeDetails()
                        .size());
    }



    @Test
    @Order(2)
    public void getEmployeeByIdTest() {

        Employee emp = empServce.getEmployeeDetails(1);
        Optional<Employee> empDB = empDao.findById(1);

        if (empDB.isPresent()) {
            assertEquals(emp.getId(), empDB.get()
                    .getId());
            assertEquals(emp.getEmail(), empDB.get()
                    .getEmail());
            assertEquals(emp.getFirstName(), empDB.get()
                    .getFirstName());
            assertEquals(emp.getLastName(), empDB.get()
                    .getLastName());
            assertEquals(emp.getPhone(), empDB.get()
                    .getPhone());
        }
    }



    @Test
    @Order(3)
    public void createEmployeeTest() throws IOException, Throwable, TimeoutException {
        Employee emp = empConstructor("Ram", "kumar", "ramTestService@evoketechnologies.com", "0000000000", "23-Apr-2021", 1, "123");
        assertEquals(jwtTokenProvider.getEmail(empServce.saveEmployeeDetails(emp)), emp.getEmail());
    }



    @Test
    @Order(4)
    public void updateEmployeeTest() {
        Employee emp = empDao.findAll()
                .get(0);
        assertEquals("employee.edit.success", empServce.updateEmployeeDetails(emp));
    }

    @Test
    @Order(5)
    public void deleteEmployeeTest() {
        assertEquals("employee.deleted", empServce.deleteEmployeeDetails(empServce.employeeEmailCheck("ramTestService@evoketechnologies.com")));
    }


    @Test
    public void getAllDepartments() throws Exception {
        assertEquals(depDao.findAll()
                .size(),
                depServce.getAllDepartments()
                        .size());
    }

    @Test
    @Order(6)
    public void getDepartmentByIdTest() {

        Department dep = depServce.getDepartmentDetails(1);
        Optional<Department> depDB = depDao.findById(1);

        if (depDB.isPresent()) {
            assertEquals(dep.getDepId(), depDB.get()
                    .getDepId());
            assertEquals(dep.getDepName(), depDB.get()
                    .getDepName());
            assertEquals(dep.getDescription(), depDB.get()
                    .getDescription());
        }
    }

    @Test
    @Order(6)
    public void saveDepartmentTest() {
        Department dep = new Department("ABC", "ABC");
        assertEquals(depServce.saveDepartment(dep), "department.save.success");

    }

    @Test
    @Order(6)
    public void updateDepartmentTest() {
        Department dep = depDao.findAll()
                .get(0);
        assertEquals(depServce.updateDepartment(dep), "department.edit.success");

    }

    @Test
    @Order(7)
    public void deleteDepartmentTest() throws Exception {
        Department dep = depServce.departmentByName("ABC");
        if (dep != null)
            assertEquals("department.deleted", depServce.deleteDepartment(dep));
    }

}
