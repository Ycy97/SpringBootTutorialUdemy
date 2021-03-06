package com.cy.springboot.cruddemo.dao;

import java.util.List;

import com.cy.springboot.cruddemo.entity.Employee;

public interface EmployeeDAO {
	
	//CRUD operations
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);
	
}
