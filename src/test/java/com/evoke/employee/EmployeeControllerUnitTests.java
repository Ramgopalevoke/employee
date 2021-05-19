package com.evoke.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import com.evoke.employee.controller.EmployeeController;
import com.evoke.employee.dto.EmployeeDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerUnitTests {


    @LocalServerPort
    private int port;


    /*
     * @Autowired(required = true) private TestRestTemplate restTemplate;
     */


    @Autowired
    EmployeeController empCon;



    @Sql({"classpath:data.sql"})
    @Test
    public void testAddEmployee() throws Exception {
        EmployeeDTO empDto = new EmployeeDTO();
        empDto.setFirstName("Rohit");
        empDto.setLastName("Sharma");
        empDto.setEmail("rohitscXxzcxcZcZc@evoketechnologies.com");
        empDto.setPhone("0000000000");
        empDto.setJoiningDate("23-apr-2021");
        empDto.setDepId(1);
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/enroll", empDto, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }


    /*
     * @Test public void testAddEmployeeWithBadRequest() throws Exception { EmployeeDTO empDto = new
     * EmployeeDTO(); empDto.setFirstName("Rohit"); empDto.setLastName("Sharma");
     * empDto.setJoiningDate("23-apr-2021"); empDto.setDepId(1); empDto.setEmail("String");
     * empDto.setPhone("String"); List<String> messages = new ArrayList<String>();
     * messages.add("Please enter valid phone number");
     * messages.add("Please enter valid email address"); ErrorResponse err = new
     * ErrorResponse(HttpStatus.BAD_REQUEST.toString(), messages);
     * 
     * when(empCon.addEmployeeDetails(empDto)).thenReturn(new
     * ResponseEntity<String>(HttpStatus.BAD_REQUEST));
     * 
     * ResponseEntity<ErrorResponse> responseEntity =
     * this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/enroll",
     * empDto, ErrorResponse.class); assertEquals(400, responseEntity.getStatusCodeValue());
     * assertEquals(err.getError(), responseEntity.getBody() .getError());
     * assertTrue(err.getMessages() .containsAll(responseEntity.getBody() .getMessages())); }
     */


    /*
     * @Test public void testGetAllEmployees() throws Exception {
     * when(empCon.getAllEmployeeDetails()).thenReturn(new
     * ResponseEntity<List<Employee>>(HttpStatus.OK));
     * assertTrue(!(this.restTemplate.getForObject("http://localhost:" + port +
     * "/evoke/v1/allemployees", List.class) .isEmpty())); }
     * 
     * @Test public void testGetAllEmployeesWithDepartment() throws Exception {
     * when(empCon.getAllEmployeeAndDepatmentDetails()).thenReturn(new
     * ResponseEntity<Stream<HashMap<String, String>>>(HttpStatus.OK));
     * assertTrue(!this.restTemplate.getForObject("http://localhost:" + port +
     * "/evoke/v1/allemployeeswithdepartments", List.class) .isEmpty()); }
     * 
     * @Test public void testUpdateEmployeeWithBadRequest() throws Exception { UpdateEmpDTO empDto =
     * new UpdateEmpDTO(); empDto.setEmail("String"); empDto.setPhone("String");
     * 
     * List<String> messages = new ArrayList<String>();
     * messages.add("Please enter valid email address");
     * messages.add("Please enter valid phone number");
     * 
     * ErrorResponse err = new ErrorResponse(); err.setError(HttpStatus.BAD_REQUEST.toString());
     * err.setMessages(messages);
     * 
     * ValidateID valId = new ValidateID(); valId.setId("1");
     * 
     * when(empCon.editEmployeeDetails(empDto, valId)).thenReturn(new
     * ResponseEntity<String>(HttpStatus.BAD_REQUEST));
     * 
     * ResponseEntity<ErrorResponse> responseEntity =
     * this.restTemplate.postForEntity("http://localhost:" + port + "/evoke/v1/employee/1", empDto,
     * ErrorResponse.class); assertEquals(400, responseEntity.getStatusCodeValue());
     * assertEquals(err.getError(), responseEntity.getBody() .getError());
     * assertEquals(err.getMessages() .size(), responseEntity.getBody() .getMessages() .size()); }
     * 
     * @Test public void testUpdateEmployee() throws Exception { UpdateEmpDTO empDto = new
     * UpdateEmpDTO("RohitUpdate", "SharmaUpdate", "rohitUpdate@evoketechnologies.com",
     * "1000000000"); ValidateID valId = new ValidateID(); valId.setId("2");
     * when(empCon.editEmployeeDetails(empDto, valId)).thenReturn(new
     * ResponseEntity<String>(HttpStatus.OK));
     * 
     * ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" +
     * port + "/evoke/v1/employee/2", empDto, String.class); assertEquals(200,
     * responseEntity.getStatusCodeValue()); }
     * 
     * @Test public void testUpdateEmployeeWithNotFound() throws Exception { UpdateEmpDTO empDto =
     * new UpdateEmpDTO("RohitUpdate", "SharmaUpdate", "rohitUpdate@evoketechnologies.com",
     * "1000000000");
     * 
     * ValidateID valId = new ValidateID(); valId.setId("100");
     * when(empCon.editEmployeeDetails(empDto, valId)).thenReturn(new
     * ResponseEntity<String>(HttpStatus.NOT_FOUND));
     * 
     * ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" +
     * port + "/evoke/v1/employee/100", empDto, String.class); assertEquals(404,
     * responseEntity.getStatusCodeValue()); }
     * 
     * @Test public void testgetEmployee() throws RestClientException, Exception { ValidateID valId
     * = new ValidateID(); valId.setId("1"); when(empCon.getEmployeeDetails(valId)).thenReturn(new
     * ResponseEntity<Employee>(HttpStatus.OK));
     * 
     * ResponseEntity<Employee> responseEntity = this.restTemplate.getForEntity("http://localhost:"
     * + port + "/evoke/v1/employee/3", Employee.class); assertEquals(200,
     * responseEntity.getStatusCodeValue()); Employee emp = responseEntity.getBody(); assertTrue(3
     * == emp.getId()); assertTrue("SREE NIVAS".equals(emp.getName()));
     * assertTrue("sree@evoketechnologies.com".equals(emp.getEmail()));
     * assertTrue("2222222222".equals(emp.getPhone())); }
     * 
     * 
     * @Test public void testgetEmployeeWithNotFound() throws Exception { ValidateID valId = new
     * ValidateID(); valId.setId("100");
     * 
     * when(empCon.getEmployeeDetails(valId)).thenReturn(new
     * ResponseEntity<Employee>(HttpStatus.NOT_FOUND));
     * 
     * ResponseEntity<Employee> responseEntity = this.restTemplate.getForEntity("http://localhost:"
     * + port + "/evoke/v1/employee/100", Employee.class); assertEquals(404,
     * responseEntity.getStatusCodeValue()); }
     * 
     * @Test public void testGetEmployeeWithInvalid() throws Exception { ValidateID valId = new
     * ValidateID(); valId.setId("a");
     * 
     * when(empCon.getEmployeeDetails(valId)).thenReturn(new
     * ResponseEntity<Employee>(HttpStatus.BAD_REQUEST));
     * 
     * ResponseEntity<Employee> responseEntity = this.restTemplate.getForEntity("http://localhost:"
     * + port + "/evoke/v1/employee/a", Employee.class); assertEquals(400,
     * responseEntity.getStatusCodeValue()); }
     * 
     * @Test public void testdeleteEmployeeWithNotFound() throws Exception { ValidateID valId = new
     * ValidateID(); valId.setId("100");
     * 
     * when(empCon.deleteEmployeeDetails(valId)).thenReturn(new
     * ResponseEntity<String>(HttpStatus.NOT_FOUND));
     * 
     * ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port
     * + "/evoke/v1/employee/100", HttpMethod.DELETE, null, String.class); assertEquals(404,
     * responseEntity.getStatusCodeValue()); }
     * 
     * 
     * @Test public void testdeleteEmployee() throws Exception {
     * 
     * ValidateID valId = new ValidateID(); valId.setId("1");
     * 
     * when(empCon.deleteEmployeeDetails(valId)).thenReturn(new
     * ResponseEntity<String>(HttpStatus.OK));
     * 
     * ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port
     * + "/evoke/v1/employee/1", HttpMethod.DELETE, null, String.class); assertEquals(200,
     * responseEntity.getStatusCodeValue()); }
     * 
     * 
     * 
     * @Test public void testEmployeeCountForDep() throws Exception {
     * 
     * when(empCon.getEmpCountForDepartments()).thenReturn(new ResponseEntity<Stream<HashMap<String,
     * Object>>>(HttpStatus.OK));
     * 
     * ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:" +
     * port + "/evoke/v1/getEmpCountsByDept", List.class); assertEquals(200,
     * responseEntity.getStatusCodeValue()); }
     */

}
