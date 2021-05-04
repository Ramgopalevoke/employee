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
import com.evoke.employee.dto.EmployeeDTO;
import com.evoke.employee.dto.SaveEmployeeDTO;
import com.evoke.employee.dto.UpdateEmpDTO;
import com.evoke.employee.dto.ValidateEmployeeID;
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

    @ApiOperation(value = "Get All Employee Details", tags = "Employee Service")
    @GetMapping("/allemployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeDetails() throws Exception {


        List<EmployeeDTO> empDTOList = empService.getAllEmployeeDetails()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPhone()))
                .sorted(Comparator.comparing(EmployeeDTO::getId))
                .collect(Collectors.toList());

        return new ResponseEntity<>(empDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Add Employee Details", tags = "Employee Service")
    @PostMapping("/employee/enroll")
    public ResponseEntity<String> addEmployeeDetails(@Valid @RequestBody SaveEmployeeDTO empDTO) throws Exception {
        if (empService.employeeEmailCheck(empDTO.getEmail()) != null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("employee.email.exist", new Object[] {empDTO.getEmail()}, null));

        return new ResponseEntity<>(
                messageSource.getMessage(empService.saveEmployeeDetails(new Employee(empDTO.getFirstName(), empDTO.getLastName(), empDTO.getEmail(), empDTO.getPhone())),
                        new Object[] {empDTO.getEmail()}, null),
                HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Employee Details", tags = "Employee Service")
    @PostMapping("/employee/{id}")
    public ResponseEntity<String> editEmployeeDetails(@Valid @RequestBody UpdateEmpDTO empDTO, @Valid @PathVariable(name = "id") ValidateEmployeeID validId) throws Exception {
        Employee emp = empService.getEmployeeDetails(validId.getId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {validId.getId()}, null));

        emp = empDTO.UpdateEmpObject(emp, empDTO);
        return new ResponseEntity<>(messageSource.getMessage(empService.updateEmployeeDetails(emp), new Object[] {emp.getName()}, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Employee Details based on id", tags = "Employee Service")
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDetails(@Valid @PathVariable(name = "id") ValidateEmployeeID validId) throws Exception {
        Employee emp = empService.getEmployeeDetails(validId.getId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {validId.getId()}, null));

        return new ResponseEntity<>(new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPhone()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Employee Details", tags = "Employee Service")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployeeDetails(@Valid @PathVariable(name = "id") ValidateEmployeeID validId) throws Exception {
        Employee emp = empService.getEmployeeDetails(validId.getId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {validId.getId()}, null));

        return new ResponseEntity<>(messageSource.getMessage(empService.deleteEmployeeDetails(validId.getId()), new Object[] {validId.getId()}, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Employee Details with departments", tags = "Employee Service")
    @GetMapping("/allemployeeswithdepartments")
    public ResponseEntity<Stream<HashMap<String, String>>> getAllEmployeeAndDepatmentDetails() throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        List<Employee> empDTOList = empService.getAllEmployeeDetails();

        Stream<HashMap<String, String>> empDTOListOp = empDTOList.stream()
                .map((emp) -> {
                    HashMap<String, String> hp = new HashMap<String, String>();
                    hp.put("employee name", emp.getName());
                    hp.put("date of joining", formatter.format(emp.getCreatedOn()));
                    hp.put("department name", emp.getDepartment() != null ? emp.getDepartment()
                            .getName() : "");
                    return hp;
                });

        return new ResponseEntity<>(empDTOListOp, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Employees count respective departments", tags = "Employee Service")
    @GetMapping("/getEmpCountsByDept")
    public ResponseEntity<Stream<HashMap<String, String>>> getEmpCountForDepartments() throws Exception {

        List<Object[]> depCntList = depService.getCountOfEmployeeForDepartment();
        Stream<HashMap<String, String>> depCntListOp = depCntList.stream()
                .map((obj) -> {
                    HashMap<String, String> hp = new HashMap<String, String>();
                    hp.put("deptName", obj[0].toString());
                    hp.put("count", obj[1].toString());
                    return hp;
                });

        return new ResponseEntity<>(depCntListOp, HttpStatus.OK);
    }
}
