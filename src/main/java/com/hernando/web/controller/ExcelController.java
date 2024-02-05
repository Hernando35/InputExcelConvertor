package com.hernando.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hernando.web.model.Employee;
import com.hernando.web.service.ExcelDataService;

@Controller
public class ExcelController {
	private static final Logger log = LoggerFactory.getLogger(ExcelController.class.getCanonicalName());

	private final ExcelDataService excelDataService;

	@Autowired
	public ExcelController(ExcelDataService excelDataService) {
		this.excelDataService = excelDataService;
	}

	@GetMapping("/index")
    public String displayEmployees(Model model) {
        List<Employee> employees = excelDataService.readExcel("src/main/resources/file.xlsx");
        log.info("Number of employees: {}", employees.size());
        model.addAttribute("employees", employees);
        return "employeeList"; // Specify the view name here
    }
}
