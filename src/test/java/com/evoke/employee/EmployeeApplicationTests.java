package com.evoke.employee;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.service.impl.EmployeeServiceImpl;

@SpringBootTest
class EmployeeApplicationTests {

    @InjectMocks
    EmployeeServiceImpl empServce;

    @Mock
    EmployeeRepository dao;


    @Test
    public void getAllEmployeesTest() throws Exception {
        List<Employee> list = new ArrayList<Employee>();
        Employee empOne = new Employee("Ram", "ram@evoketechnologies.com", "0000000000");
        Employee empTwo = new Employee("gopal", "gopal@evoketechnologies.com", "1111111111");
        Employee empThree = new Employee("srinivas", "sree@evoketechnologies.com", "2222222222");
        Employee empFour = new Employee("vishnu", "vishnu@evoketechnologies.com", "3333333333");

        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);
        list.add(empFour);

        when(dao.findAll()).thenReturn(list);

        // test
        List<Employee> empList = null;
        empList = empServce.getAllEmployeeDetails();

        assertEquals(4, empList.size());
        verify(dao, times(1)).findAll();
    }

    @Test
    public void getEmployeeByIdTest() {
        Employee empTest = new Employee("Ram", "ram@evoketechnologies.com", "0000000000");
        empTest.setCreatedBy("System");
        empTest.setId(1);
        Optional<Employee> empOpt = Optional.of(empTest);
        when(dao.findById(1)).thenReturn(empOpt);

        Employee emp = empServce.getEmployeeDetails(1);

        assertEquals(1, emp.getId());
        assertEquals("Ram", emp.getName());
        assertEquals("0000000000", emp.getPhone());
        assertEquals("ram@evoketechnologies.com", emp.getEmail());
        assertEquals("System", emp.getCreatedBy());
    }


    @Test
    public void createEmployeeTest() {
        Employee emp = new Employee("Ram", "ram@evoketechnologies.com", "0000000000");
        empServce.saveEmployeeDetails(emp);
        verify(dao, times(1)).save(emp);
    }

    @Test
    public void updateEmployeeTest() {
        Employee emp = new Employee("RamUpdated", "ram@evoketechnologies.com", "0000000000");
        empServce.updateEmployeeDetails(emp);
        verify(dao, times(1)).saveAndFlush(emp);
    }

    @Test
    public void deleteEmployeeTest() {
        empServce.deleteEmployeeDetails(5);
        verify(dao, times(1)).deleteById(5);
    }

}
