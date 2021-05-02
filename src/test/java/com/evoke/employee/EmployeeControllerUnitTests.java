package com.evoke.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClientException;
import com.evoke.employee.ExceptionHandler.ErrorResponse;
import com.evoke.employee.controller.EmployeeController;
import com.evoke.employee.dto.EmployeeDTO;
import com.evoke.employee.dto.SaveEmployeeDTO;
import com.evoke.employee.dto.UpdateEmpDTO;
import com.evoke.employee.dto.ValidateEmployeeID;


public class EmployeeControllerUnitTests extends EmployeeApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired(required = true)
    private TestRestTemplate restTemplate;

    @Mock
    EmployeeController empCon;

    @Sql({"classpath:data.sql"})
    @Test
    public void testAddEmployee() throws Exception {
        SaveEmployeeDTO empDto = new SaveEmployeeDTO();
        empDto.setName("Rohit");
        empDto.setEmail("rohit@evoketechnologies.com");
        empDto.setPhone("0000000000");
        when(empCon.addEmployeeDetails(empDto)).thenReturn(new ResponseEntity<String>(HttpStatus.CREATED));
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/enroll", empDto, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testAddEmployeeWithBadRequest() throws Exception {
        SaveEmployeeDTO empDto = new SaveEmployeeDTO("Rohit", "String", "String");
        List<String> messages = new ArrayList<String>();
        messages.add("Please enter valid phone number");
        messages.add("Please enter valid email address");
        ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), messages);

        when(empCon.addEmployeeDetails(empDto)).thenReturn(new ResponseEntity<String>(HttpStatus.BAD_REQUEST));

        ResponseEntity<ErrorResponse> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/enroll", empDto, ErrorResponse.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(err.getError(), responseEntity.getBody()
                .getError());
        assertEquals(err.getMessages()
                .size(),
                responseEntity.getBody()
                        .getMessages()
                        .size());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        when(empCon.getAllEmployeeDetails()).thenReturn(new ResponseEntity<List<EmployeeDTO>>(HttpStatus.OK));
        assertTrue(this.restTemplate.getForObject("http://localhost:" + port + "/evoke/v1/allemployees", List.class)
                .size() == 5);
    }


    @Test
    public void testUpdateEmployeeWithBadRequest() throws Exception {
        UpdateEmpDTO empDto = new UpdateEmpDTO();
        empDto.setEmail("String");
        empDto.setPhone("String");

        List<String> messages = new ArrayList<String>();
        messages.add("Please enter valid email address");
        messages.add("Please enter valid phone number");

        ErrorResponse err = new ErrorResponse();
        err.setError(HttpStatus.BAD_REQUEST.toString());
        err.setMessages(messages);

        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("5");

        when(empCon.editEmployeeDetails(empDto, valId)).thenReturn(new ResponseEntity<String>(HttpStatus.BAD_REQUEST));

        ResponseEntity<ErrorResponse> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/5", empDto, ErrorResponse.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(err.getError(), responseEntity.getBody()
                .getError());
        assertEquals(err.getMessages()
                .size(),
                responseEntity.getBody()
                        .getMessages()
                        .size());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        UpdateEmpDTO empDto = new UpdateEmpDTO("RohitUpdate", "rohitUpdate@evoketechnologies.com", "1000000000");
        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("5");
        when(empCon.editEmployeeDetails(empDto, valId)).thenReturn(new ResponseEntity<String>(HttpStatus.OK));

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/5", empDto, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateEmployeeWithNotFound() throws Exception {
        UpdateEmpDTO empDto = new UpdateEmpDTO("RohitUpdate", "rohitUpdate@evoketechnologies.com", "1000000000");

        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("100");
        when(empCon.editEmployeeDetails(empDto, valId)).thenReturn(new ResponseEntity<String>(HttpStatus.NOT_FOUND));

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/100", empDto, String.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testgetEmployee() throws RestClientException, Exception {
        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("5");
        when(empCon.getEmployeeDetails(valId)).thenReturn(new ResponseEntity<EmployeeDTO>(HttpStatus.OK));

        ResponseEntity<EmployeeDTO> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/evoke/v1/employee/5", EmployeeDTO.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        EmployeeDTO emp = responseEntity.getBody();
        assertTrue(5 == emp.getId());
        assertTrue("RohitUpdate".equals(emp.getName()));
        assertTrue("rohitUpdate@evoketechnologies.com".equals(emp.getEmail()));
        assertTrue("1000000000".equals(emp.getPhone()));
    }


    @Test
    public void testgetEmployeeWithNotFound() throws Exception {
        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("100");

        when(empCon.getEmployeeDetails(valId)).thenReturn(new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_FOUND));

        ResponseEntity<EmployeeDTO> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/evoke/v1/employee/100", EmployeeDTO.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetEmployeeWithInvalid() throws Exception {
        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("a");

        when(empCon.getEmployeeDetails(valId)).thenReturn(new ResponseEntity<EmployeeDTO>(HttpStatus.BAD_REQUEST));

        ResponseEntity<EmployeeDTO> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/evoke/v1/employee/a", EmployeeDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testdeleteEmployeeWithNotFound() throws Exception {
        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("100");

        when(empCon.deleteEmployeeDetails(valId)).thenReturn(new ResponseEntity<String>(HttpStatus.NOT_FOUND));

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/evoke/v1/employee/100", HttpMethod.DELETE, null, String.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testdeleteEmployee() throws Exception {

        ValidateEmployeeID valId = new ValidateEmployeeID();
        valId.setId("1");

        when(empCon.deleteEmployeeDetails(valId)).thenReturn(new ResponseEntity<String>(HttpStatus.OK));

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/evoke/v1/employee/1", HttpMethod.DELETE, null, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
