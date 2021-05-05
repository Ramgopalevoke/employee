package com.evoke.employee;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
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

    /*
     * @Override public void run(String... params) throws Exception { User admin = new User();
     * admin.setUsername("admin"); admin.setPassword("admin"); admin.setEmail("admin@email.com");
     * admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
     * 
     * userService.signup(admin);
     * 
     * User client = new User(); client.setUsername("client"); client.setPassword("client");
     * client.setEmail("client@email.com"); client.setRoles(new
     * ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
     * 
     * userService.signup(client); }
     */

}
