package com.evoke.employee.service;

import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.evoke.employee.ExceptionHandler.CustomException;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.security.JwtTokenProvider;

@Service
public class EmployeeSecurityService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String signin(String username, String password) {
        Optional<Employee> emp = empRepo.findByEmail(username);
        var map = new HashMap<>();
        if (emp.isPresent()) {
            map.put("email", emp.get()
                    .getEmail());
            return jwtTokenProvider.createToken(map);
        } else {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }



    public Optional<Employee> whoami(HttpServletRequest req) {
        return empRepo.findByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(HttpServletRequest req) {
        Optional<Employee> emp = empRepo.findByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(req)));
        var map = new HashMap<>();
        if (emp.isPresent()) {
            map.put("email", emp.get()
                    .getEmail());
        }
        return jwtTokenProvider.createToken(map);
    }

}
