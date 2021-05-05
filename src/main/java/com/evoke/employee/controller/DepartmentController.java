package com.evoke.employee.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
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
import com.evoke.employee.entity.Department;
import com.evoke.employee.service.DepartmentService;
import io.micrometer.core.lang.NonNull;
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

    @Autowired
    private ModelMapper mapper;

    @ApiOperation(value = "Get All Departments")
    @GetMapping("/allDepartments")
    public ResponseEntity<List<Department>> getAllDepartmentDetails() throws Exception {

        return new ResponseEntity<>(depService.getAllDepartments()
                .stream()
                .sorted(Comparator.comparing(Department::getDepId))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @ApiOperation(value = "Add Department")
    @PostMapping("/addDepartment")
    public ResponseEntity<String> addDepartmentDetails(@Valid @RequestBody DepartmentDTO depDTO) throws Exception {
        if (depService.departmentByName(depDTO.getDepName()) != null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.name.exist", new Object[] {depDTO.getDepName()}, null));

        return new ResponseEntity<>(messageSource.getMessage(depService.saveDepartment(mapper.map(depDTO, Department.class)), new Object[] {depDTO.getDepName()}, null),
                HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Depatment")
    @PostMapping("/department/{depId}")
    public ResponseEntity<String> editDepartmentDetails(@Valid @RequestBody DepartmentDTO depDTO, @NonNull @NotEmpty @PathVariable(name = "depId") String depId) throws Exception {
        var dep = depService.getDepartmentDetails(validateDepId(depId));
        if (dep == null)
            throw new RecordNotFoundException(messageSource.getMessage("department.notfound", new Object[] {depId}, null));
        if (depService.departmentByName(depDTO.getDepName()) != null)
            throw new InvalidIdFormatExeption(messageSource.getMessage("department.name.exist", new Object[] {depDTO.getDepName()}, null));

        depDTO.setDepId(dep.getDepId());
        depDTO.setCreatedBy(dep.getCreatedBy());
        depDTO.setCreatedOn(dep.getCreatedOn());

        return new ResponseEntity<>(messageSource.getMessage(depService.updateDepartment(mapper.map(depDTO, Department.class)), new Object[] {depDTO.getDepName()}, null),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Get Department based on depId")
    @GetMapping("/department/{depId}")
    public ResponseEntity<Department> getDepartmentDetails(@NonNull @NotEmpty @PathVariable(name = "depId") String depId) throws Exception {
        var dep = depService.getDepartmentDetails(validateDepId(depId));
        if (dep == null)
            throw new RecordNotFoundException(messageSource.getMessage("department.notfound", new Object[] {depId}, null));

        return new ResponseEntity<>(dep, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Department")
    @DeleteMapping("/department/{depId}")
    public ResponseEntity<String> deleteDepartmentDetails(@NonNull @NotEmpty @PathVariable(name = "depId") String depId) throws Exception {
        var dep = depService.getDepartmentDetails(validateDepId(depId));
        if (dep == null)
            throw new RecordNotFoundException(messageSource.getMessage("department.notfound", new Object[] {depId}, null));

        return new ResponseEntity<>(messageSource.getMessage(depService.deleteDepartment(dep), new Object[] {depId}, null), HttpStatus.OK);
    }


    @ApiOperation(value = "Get Employees count respective departments")
    @GetMapping("/getEmpCountsByDept")
    public ResponseEntity<Stream<HashMap<String, Object>>> getEmpCountForDepartments() throws Exception {

        List<Department> depCntList = depService.getAllDepartments();
        Stream<HashMap<String, Object>> depCntListOp = depCntList.stream()
                .map((obj) -> {
                    HashMap<String, Object> hp = new HashMap<>();
                    hp.put("deptName", obj.getDepName());
                    hp.put("count", obj.getEmployees()
                            .size());
                    return hp;
                });

        return new ResponseEntity<>(depCntListOp, HttpStatus.OK);
    }

    public int validateDepId(String depId) {
        if (!(depId.matches("^[0-9]*$")))
            throw new InvalidIdFormatExeption("Department Id should be numeric");
        else
            return Integer.valueOf(depId);
    }
}
