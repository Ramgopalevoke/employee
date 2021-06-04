package com.evoke.employee;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.service.DepartmentService;
import com.evoke.employee.service.EmployeeService;

@SpringBootApplication
public class EmployeeApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeService empService;

    @Autowired
    DepartmentService depService;

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
    @Order(value = 1)
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    @Order(value = 2)
    public void run(String... params) throws Exception {
        logger.debug("Check and creation of admin login");
        if (empService.employeeEmailCheck("admin@email.com") == null) {
            Employee admin = new Employee();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setDepId(1);
            admin.setDepartment(depService.getDepartmentDetails(1));
            admin.setEmail("admin@email.com");
            admin.setJoiningDate("24-Apr-2021");
            admin.setPassword("admin");
            
            empService.saveEmployeeDetails(admin);
            logger.debug("Created admin employee successfully");

        }
    }

}
