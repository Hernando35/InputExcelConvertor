package com.hernando.web.service;

import java.util.List;

import com.hernando.web.model.Employee;

public interface ExcelDataService {
    List<Employee> readExcel(String filePath);
}
