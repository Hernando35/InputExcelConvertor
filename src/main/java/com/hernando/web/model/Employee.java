package com.hernando.web.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_data")
public class Employee {

	@Id
	@Column(name = "employee_id")
	private long employee_id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@Column(name = "department")
	private String department;

	@Column(name = "address")
	private String address;
	
	

	public Employee(long employee_id, String name, int age, String department, String address) {
		super();
		this.employee_id = employee_id;
		this.name = name;
		this.age = age;
		this.department = department;
		this.address = address;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(long employee_id) {
		this.employee_id = employee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
