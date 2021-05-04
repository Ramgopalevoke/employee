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
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.service.impl.DepartmentServiceImpl;
import com.evoke.employee.service.impl.EmployeeServiceImpl;

class EmployeeServiceUnitTests extends EmployeeApplicationTests {

    @InjectMocks
    EmployeeServiceImpl empServce;

    @InjectMocks
    DepartmentServiceImpl depServce;

    @Mock
    EmployeeRepository empDao;

    @Mock
    DepartmentRepository depDao;


    @Test
    public void getAllEmployeesTest() throws Exception {
        List<Employee> list = new ArrayList<Employee>();
        list.add(new Employee("Ram", "kumar", "ram@evoketechnologies.com", "0000000000"));
        list.add(new Employee("gopal", "krishna", "gopal@evoketechnologies.com", "1111111111"));
        list.add(new Employee("sree", "nivas", "sree@evoketechnologies.com", "2222222222"));
        list.add(new Employee("vishnu", "varma", "vishnu@evoketechnologies.com", "3333333333"));

        when(empDao.findAll()).thenReturn(list);

        // test
        List<Employee> empList = null;
        empList = empServce.getAllEmployeeDetails();

        assertEquals(4, empList.size());
        verify(empDao, times(1)).findAll();
    }

    @Test
    public void getEmployeeByIdTest() {
        Employee empTest = new Employee("ram", "kumar", "ram@evoketechnologies.com", "0000000000");
        empTest.setName("RAM KUMAR");
        empTest.setCreatedBy("System");
        empTest.setId(1);
        Optional<Employee> empOpt = Optional.of(empTest);
        when(empDao.findById(1)).thenReturn(empOpt);

        Employee emp = empServce.getEmployeeDetails(1);

        assertEquals(1, emp.getId());
        assertEquals("RAM KUMAR", emp.getName());
        assertEquals("ram", emp.getFirstName());
        assertEquals("kumar", emp.getLastName());
        assertEquals("0000000000", emp.getPhone());
        assertEquals("ram@evoketechnologies.com", emp.getEmail());
        assertEquals("System", emp.getCreatedBy());
    }


    @Test
    public void createEmployeeTest() {
        Employee emp = new Employee("Ram", "kumar", "ram@evoketechnologies.com", "0000000000");
        String successMsg = empServce.saveEmployeeDetails(emp);
        verify(empDao, times(1)).save(emp);
        assertEquals("employee.save.success", successMsg);
    }

    @Test
    public void updateEmployeeTest() {
        Employee emp = new Employee("RamUpdated", "kumarUpdated", "ram@evoketechnologies.com", "0000000000");
        String successMsg = empServce.updateEmployeeDetails(emp);
        verify(empDao, times(1)).saveAndFlush(emp);
        assertEquals("employee.edit.success", successMsg);
    }

    @Test
    public void deleteEmployeeTest() {
        String successMsg = empServce.deleteEmployeeDetails(5);
        verify(depDao, times(0)).deleteById(5);
        verify(empDao, times(1)).deleteById(5);
        assertEquals("employee.deleted", successMsg);
    }

    @Test
    public void departmentEmpCountTest() throws Exception {
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] hp = new Object[2];
        hp[0] = "TAG";
        hp[1] = "1";
        list.add(hp);
        hp = (Object[]) new Object[2];
        hp[0] = "HR";
        hp[1] = "1";
        list.add(hp);
        hp = (Object[]) new Object[2];
        hp[0] = "IT";
        hp[1] = "2";
        list.add(hp);
        list.add(hp);

        when(depDao.countEmployeesForDepartment()).thenReturn(list);

        List<Object[]> obj = depServce.getCountOfEmployeeForDepartment();
        verify(depDao, times(1)).countEmployeesForDepartment();
        assertEquals(4, obj.size());
    }

}
