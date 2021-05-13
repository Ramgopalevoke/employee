package com.evoke.employee.controller;

import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.evoke.employee.dto.EmployeeDTO;
import com.evoke.employee.service.EmployeeSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;


@RestController
@RequestMapping("/evoke/v1/")
@Api(tags = "EmployeeSecurity")
public class EmployeeAuthenticationController {

    @Autowired
    private EmployeeSecurityService empService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "Authenticates Employee and returns its JWT token.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid email/password supplied")})
    public String login(//
            @ApiParam("email") @RequestParam String email, //
            @ApiParam("Password") @RequestParam String password) {
        return empService.signin(email, password);
    }


    @GetMapping(value = "/me")
    @ApiOperation(value = "Returns current user's data", response = EmployeeDTO.class, authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public EmployeeDTO whoami(HttpServletRequest req) {
        return modelMapper.map(empService.whoami(req)
                .get(), EmployeeDTO.class);
    }

    @GetMapping("/refresh")
    public String refresh(HttpServletRequest req) {
        return empService.refresh(req);
    }

}
