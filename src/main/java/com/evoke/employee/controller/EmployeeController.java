package com.evoke.employee.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.evoke.employee.ExceptionHandler.RecordNotFoundException;
import com.evoke.employee.dto.EmployeeDTO;
import com.evoke.employee.dto.SaveEmployeeDTO;
import com.evoke.employee.dto.UpdateEmpDTO;
import com.evoke.employee.dto.ValidateEmployeeID;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.service.EmployeeService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "evoke/v1")
public class EmployeeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeService empService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @ApiOperation(value = "Get All Employee Details", tags = "Employee Service")
    @GetMapping("/allemployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeDetails() throws Exception {

        LOGGER.info("Entered into Get All employees method");

        List<EmployeeDTO> empDTOList = empService.getAllEmployeeDetails()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPhone()))
                .collect(Collectors.toList());

        LOGGER.info("Before Getting response from All employees method");

        return new ResponseEntity<>(empDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Add Employee Details", tags = "Employee Service")
    @PostMapping("/employee/enroll")
    public ResponseEntity<String> addEmployeeDetails(@Valid @RequestBody SaveEmployeeDTO empDTO) throws Exception {
        return new ResponseEntity<>(messageSource.getMessage(empService.saveEmployeeDetails(new Employee(empDTO.getName(), empDTO.getEmail(), empDTO.getPhone())),
                new Object[] {empDTO.getName()}, null), HttpStatus.CREATED);
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
}
