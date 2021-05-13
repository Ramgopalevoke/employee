package com.evoke.employee.security;

import java.util.ArrayList;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;

@Service
public class MyUserDetails implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeRepository empRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Employee> user = empRepository.findByEmail(username);

        if (!user.isPresent()) {
            log.error("Failed");
            throw new UsernameNotFoundException("Employee '" + username + "' not found");
        }

        log.info("Success");
        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(user.get()
                        .getPassword())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .authorities(new ArrayList())
                .build();
    }

}
