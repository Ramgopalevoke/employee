package com.evoke.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.InputStreamResource;
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
import com.evoke.employee.entity.Employee;
import com.evoke.employee.service.DepartmentService;
import com.evoke.employee.service.EmployeeService;
import com.evoke.employee.utility.EmployeeExporterFactory;
import io.micrometer.core.lang.NonNull;
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
    private ModelMapper mapper;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;


    @ApiOperation(value = "Get All Employee Details")
    @GetMapping("/allemployees")
    public ResponseEntity<List<Employee>> getAllEmployeeDetails() throws Exception {
        return new ResponseEntity<>(empService.getAllEmployeeDetails(), HttpStatus.OK);
    }

    @ApiOperation(value = "Add Employee Details")
    @PostMapping("/employee/enroll")
    public ResponseEntity<String> addEmployeeDetails(@Valid @RequestBody EmployeeDTO empDTO) throws Exception {
        if (empService.employeeEmailCheck(empDTO.getEmail()) != null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("employee.email.exist", new Object[] {empDTO.getEmail()}, null));
        var dep = depService.getDepartmentDetails(empDTO.getDepId());
        if (dep == null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.id.notexist", new Object[] {empDTO.getDepId()}, null));

        empDTO.setDepartment(dep);
        mapper.getConfiguration()
                .setAmbiguityIgnored(true);
        return new ResponseEntity<>(empService.saveEmployeeDetails(mapper.map(empDTO, Employee.class)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Employee Details")
    @PostMapping("/employee/{id}")
    public ResponseEntity<String> editEmployeeDetails(@Valid @RequestBody EmployeeDTO empDTO, @NonNull @NotEmpty @PathVariable(name = "id") String id) throws Exception {

        var emp = empService.getEmployeeDetails(validateId(id));
        var dep = depService.getDepartmentDetails(empDTO.getDepId());
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {id}, null));
        if (dep == null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.id.notexist", new Object[] {empDTO.getDepId()}, null));

        empDTO.setId(emp.getId());
        empDTO.setCreatedOn(emp.getCreatedOn());
        empDTO.setCreatedBy(emp.getCreatedBy());
        empDTO.setDepartment(dep);

        mapper.getConfiguration()
                .setAmbiguityIgnored(true);
        return new ResponseEntity<>(messageSource.getMessage(empService.updateEmployeeDetails(mapper.map(empDTO, Employee.class)), new Object[] {emp.getName()}, null),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Get Employee Details based on id")
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeDetails(@Valid @NonNull @NotEmpty @PathVariable(name = "id") String id) throws Exception {
        var emp = empService.getEmployeeDetails(validateId(id));
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {id}, null));

        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Employee Details")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployeeDetails(@Valid @NonNull @NotEmpty @PathVariable(name = "id") String id) throws Exception {
        var emp = empService.getEmployeeDetails(validateId(id));
        if (emp == null)
            throw new RecordNotFoundException(messageSource.getMessage("employee.notfound", new Object[] {id}, null));

        return new ResponseEntity<>(messageSource.getMessage(empService.deleteEmployeeDetails(emp), new Object[] {id}, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Employee Details with departments")
    @GetMapping("/allemployeeswithdepartments")
    public ResponseEntity<Stream<HashMap<String, String>>> getAllEmployeeAndDepatmentDetails() throws Exception {


        List<Employee> empDTOList = empService.getAllEmployeeDetails();

        Stream<HashMap<String, String>> empDTOListOp = empDTOList.stream()
                .map((emp) -> {
                    HashMap<String, String> hp = new HashMap<String, String>();
                    hp.put("employee name", emp.getName());
                    hp.put("date of joining", emp.getJoiningDate());
                    hp.put("department name", emp.getDepartment() != null ? emp.getDepartment()
                            .getDepName() : "");
                    return hp;
                });

        return new ResponseEntity<>(empDTOListOp, HttpStatus.OK);
    }

    @ApiOperation(value = "Export Employee details")
    @GetMapping("/employee/export/{format}")
    public ResponseEntity<InputStreamResource> exportToExcel(HttpServletResponse response, @NonNull @NotEmpty @PathVariable(name = "format") String format) throws Exception {

        List<Employee> listUsers = empService.getAllEmployeeDetails();
        return new EmployeeExporterFactory(listUsers, format).getExporter()
                .export();
    }

    public int validateId(String id) {
        if (!(id.matches("^[0-9]*$")))
            throw new InvalidIdFormatExeption("Employee id should be numeric");
        else
            return Integer.valueOf(id);
    }

}
