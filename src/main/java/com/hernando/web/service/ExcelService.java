package com.hernando.web.service;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hernando.web.model.Employee;
import com.hernando.web.repository.EmployeeRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelService implements ExcelDataService {
	private static final Logger log = LoggerFactory.getLogger(ExcelService.class.getCanonicalName());

	@Autowired
	private EmployeeRepository repository;

	public List<Employee> readExcel(String filePath) {
		List<Employee> employees = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)))) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();

			// Skip header row
			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				employees.add(mapRowToEmployee(row));
			}
			repository.saveAll(employees);
		} catch (Exception e) {
			log.error("Error reading Excel file or saving data to database", e);
		}

		return employees;
	}

	private Employee mapRowToEmployee(Row row)  {
		Employee employee = new Employee();
		setCellData(row.getCell(0), CellType.NUMERIC, value -> {
			employee.setEmployee_id(convertToLong(value));
		});
		setCellData(row.getCell(1), CellType.STRING, value -> employee.setName((String) value));
		setCellData(row.getCell(2), CellType.NUMERIC, value -> employee.setAge(convertToInt(value)));
		setCellData(row.getCell(3), CellType.STRING, value -> employee.setDepartment((String) value));
		setCellData(row.getCell(4), CellType.STRING, value -> employee.setAddress((String) value));

		return employee;

	}

	private void setCellData(Cell cell, CellType expectedType, DataSetter<Object> dataSetter) {
		Optional.ofNullable(cell).filter(c -> c.getCellType() == expectedType).map(this::getCellValue)
				.ifPresent(dataSetter::setData);
	}

	private Object getCellValue(Cell cell) {
	    if (cell.getCellType() == CellType.STRING) {
	        return cell.getStringCellValue();
	    } else if (cell.getCellType() == CellType.NUMERIC) {
	        if (DateUtil.isCellDateFormatted(cell)) {
	            return Optional.of(cell.getDateCellValue());
	        } else {
	            return cell.getNumericCellValue();
	        }
	    }
	    return Optional.empty();  
	}


	private Long convertToLong(Object value) {
		try {
			if (value instanceof Long) {
				return (Long) value;
			} else if (value instanceof Double) {
				return ((Double) value).longValue();
			}
			return 0L; // Default value
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("exception ocurred:" + e.getMessage());
		}
	}

	private Integer convertToInt(Object value) {
		try {
			if (value instanceof Integer) {
				return (Integer) value;
			} else if (value instanceof Double) {
				return ((Double) value).intValue();
			}
			return 0; // Default value
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("exception ocurred:" + e.getMessage());
		}
	}

	// Functional interface to set data for various fields in Employee
	@FunctionalInterface
	private interface DataSetter<T> {
		void setData(T value);
	}

}
