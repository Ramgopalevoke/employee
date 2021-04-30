package com.evoke.employee.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evoke.employee.dto.EmployeeDTO;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository empRepo;
	
	@Override
	public List<Employee> getAllEmployeeDetails() throws Exception {
		// TODO Auto-generated method stub
		try {
			return empRepo.findAll();
		} catch (Exception e) {
			throw e ;
		}
	}
	
	@Override
	public Employee getEmployeeDetails(int id) {
		try {
			return empRepo.findById(id).orElse(null);
		} catch (Exception e) {
			throw e ;
		}
	}

	@Override
	public String deleteEmployeeDetails(int id) {
		// TODO Auto-generated method stub
		try {
			empRepo.deleteById(id);
		} catch (Exception e) {
			throw e;
		}
		return "employee.deleted";
	}

	@Transactional
	public String saveEmployeeDetails(EmployeeDTO empDTO) {
		Employee emp = new Employee();
		String returnMsg = "employee.save.success";
		try {
			if(empDTO.getId()>0) {
				emp.setId(empDTO.getId());
				returnMsg = "employee.edit.success";
			}
			emp.setName(empDTO.getName());
			emp.setEmail(empDTO.getEmail());
			emp.setPhone(empDTO.getPhone());
			emp.setCreatedBy("System");
			emp.setCreatedOn(new Date());
			empRepo.save(emp);
			
		} catch (Exception e) {
			 throw e ;
		}
		return returnMsg;
	}

}
