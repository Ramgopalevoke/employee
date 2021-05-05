package com.evoke.employee.controller;

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
import com.evoke.employee.dto.DepartmentDTO;
import com.evoke.employee.dto.ValidateID;
import com.evoke.employee.entity.Department;
import com.evoke.employee.service.DepartmentService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "evoke/v1")
public class DepartmentController {


    @Autowired
    DepartmentService depService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;


    @ApiOperation(value = "Get All Departments", tags = "Department Service")
    @GetMapping("/allDepartments")
    public ResponseEntity<List<Department>> getAllDepartmentDetails() throws Exception {

        return new ResponseEntity<>(depService.getAllDepartments()
                .stream()
                .sorted(Comparator.comparing(Department::getDepId))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @ApiOperation(value = "Add Department", tags = "Department Service")
    @PostMapping("/addDepartment")
    public ResponseEntity<String> addDepartmentDetails(@Valid @RequestBody DepartmentDTO depDTO) throws Exception {
        if (depService.departmentByName(depDTO.getDepartmentName()) != null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.name.exist", new Object[] {depDTO.getDepartmentName()}, null));

        return new ResponseEntity<>(messageSource.getMessage(depService.saveDepartment(new Department(depDTO.getDepartmentName(), depDTO.getDescription())),
                new Object[] {depDTO.getDepartmentName()}, null), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Depatment", tags = "Department Service")
    @PostMapping("/department/{id}")
    public ResponseEntity<String> editDepartmentDetails(@Valid @RequestBody DepartmentDTO depDTO, @Valid @PathVariable(name = "id") ValidateID validId) throws Exception {
        Department dep = depService.getDepartmentDetails(validId.getId());
        if (dep == null)
            throw new RecordNotFoundException(messageSource.getMessage("department.notfound", new Object[] {validId.getId()}, null));
        dep = depDTO.UpdateDepDTO(dep, depDTO);
        return new ResponseEntity<>(messageSource.getMessage(depService.updateDepartment(dep), new Object[] {depDTO.getDepartmentName()}, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Department based on id", tags = "Department Service")
    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentDetails(@Valid @PathVariable(name = "id") ValidateID validId) throws Exception {
        Department dep = depService.getDepartmentDetails(validId.getId());
        if (dep == null)
            throw new RecordNotFoundException(messageSource.getMessage("department.notfound", new Object[] {validId.getId()}, null));

        return new ResponseEntity<>(new DepartmentDTO(dep.getDepId(), dep.getDepName(), dep.getDescription()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Department", tags = "Department Service")
    @DeleteMapping("/department/{id}")
    public ResponseEntity<String> deleteDepartmentDetails(@Valid @PathVariable(name = "id") ValidateID validId) throws Exception {
        Department dep = depService.getDepartmentDetails(validId.getId());
        if (dep == null)
            throw new RecordNotFoundException(messageSource.getMessage("department.notfound", new Object[] {validId.getId()}, null));

        return new ResponseEntity<>(messageSource.getMessage(depService.deleteDepartment(dep), new Object[] {validId.getId()}, null), HttpStatus.OK);
    }


    @ApiOperation(value = "Get Employees count respective departments", tags = "Department Service")
    @GetMapping("/getEmpCountsByDept")
    public ResponseEntity<Stream<HashMap<String, Object>>> getEmpCountForDepartments() throws Exception {

        List<Department> depCntList = depService.getAllDepartments();
        Stream<HashMap<String, Object>> depCntListOp = depCntList.stream()
                .map((obj) -> {
                    HashMap<String, Object> hp = new HashMap<String, Object>();
                    hp.put("deptName", obj.getDepName());
                    hp.put("count", obj.getEmployees()
                            .size());
                    return hp;
                });

        return new ResponseEntity<>(depCntListOp, HttpStatus.OK);
    }
}
