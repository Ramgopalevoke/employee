package com.evoke.employee.controller;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evoke.employee.ExceptionHandler.InvalidIdFormatExeption;
import com.evoke.employee.ExceptionHandler.RecordNotFoundException;
import com.evoke.employee.dto.SaveEmployeeDTO;
import com.evoke.employee.dto.UpdateEmpDTO;
import com.evoke.employee.dto.ValidateID;
import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.service.DepartmentService;
import com.evoke.employee.service.EmployeeService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "evoke/v1")
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    @Autowired
    DepartmentService depService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

    @ApiOperation(value = "Get All Employee Details", tags = "Employee Service")
    @GetMapping("/allemployees")
    public ResponseEntity<List<Employee>> getAllEmployeeDetails() throws Exception {

        return new ResponseEntity<>(empService.getAllEmployeeDetails()
                .stream()
                .map((emp) -> {
                    emp.setDateOfJoining(formatter.format(emp.getDoj()));
                    return emp;
                })
                .sorted(Comparator.comparing(Employee::getId))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @ApiOperation(value = "Add Employee Details", tags = "Employee Service")
    @PostMapping("/employee/enroll")
    public ResponseEntity<String> addEmployeeDetails(@Valid @RequestBody SaveEmployeeDTO empDTO) throws Exception {
        if (empService.employeeEmailCheck(empDTO.getEmail()) != null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("employee.email.exist", new Object[] {empDTO.getEmail()}, null));
        Department dep = depService.getDepartmentDetails(empDTO.getDepId());
        if (dep == null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.id.notexist", new Object[] {empDTO.getDepId()}, null));

        return new ResponseEntity<>(messageSource.getMessage(empService.saveEmployeeDetails(new Employee(empDTO.getFirstName(), empDTO.getLastName(), empDTO.getEmail(),
                empDTO.getPhone(), formatter.parse(empDTO.getDateOfJoining()), empDTO.getDepId(), dep)), new Object[] {empDTO.getEmail()}, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Employee Details", tags = "Employee Service")
    @PostMapping("/employee/{id}")
    public ResponseEntity<String> editEmployeeDetails(@Valid @RequestBody UpdateEmpDTO empDTO, @Valid @PathVariable(name = "id") ValidateID validId) throws Exception {
        Employee emp = empService.getEmployeeDetails(validId.getId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {validId.getId()}, null));
        if (depService.getDepartmentDetails(empDTO.getDepId()) == null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.id.notexist", new Object[] {empDTO.getDepId()}, null));
        emp = empDTO.UpdateEmpObject(emp, empDTO);
        return new ResponseEntity<>(messageSource.getMessage(empService.updateEmployeeDetails(emp), new Object[] {emp.getName()}, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Employee Details based on id", tags = "Employee Service")
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeDetails(@Valid @PathVariable(name = "id") ValidateID validId) throws Exception {
        Employee emp = empService.getEmployeeDetails(validId.getId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {validId.getId()}, null));

        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Employee Details", tags = "Employee Service")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployeeDetails(@Valid @PathVariable(name = "id") ValidateID validId) throws Exception {
        Employee emp = empService.getEmployeeDetails(validId.getId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {validId.getId()}, null));

        return new ResponseEntity<>(messageSource.getMessage(empService.deleteEmployeeDetails(validId.getId()), new Object[] {validId.getId()}, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Employee Details with departments", tags = "Employee Service")
    @GetMapping("/allemployeeswithdepartments")
    public ResponseEntity<Stream<HashMap<String, String>>> getAllEmployeeAndDepatmentDetails() throws Exception {


        List<Employee> empDTOList = empService.getAllEmployeeDetails();

        Stream<HashMap<String, String>> empDTOListOp = empDTOList.stream()
                .map((emp) -> {
                    HashMap<String, String> hp = new HashMap<String, String>();
                    hp.put("employee name", emp.getName());
                    hp.put("date of joining", formatter.format(emp.getDoj()));
                    hp.put("department name", emp.getDepartment() != null ? emp.getDepartment()
                            .getDepName() : "");
                    return hp;
                });

        return new ResponseEntity<>(empDTOListOp, HttpStatus.OK);
    }


}
