package com.hernando.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hernando.web.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
