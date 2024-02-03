package com.hernando.web.inputexcelconvertor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hernando.web.model.Employee;
import com.hernando.web.repository.EmployeeRepository;
import com.hernando.web.service.ExcelDataServiceImp;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ExcelDataServiceImpTest {

	private ExcelDataServiceImp excelDataService;

	@Mock
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		excelDataService = new ExcelDataServiceImp(employeeRepository);
	}

	@Test
	void testReadExcel() throws IOException {
		// Assuming you have a test Excel file in your resources folder
		String testFilePath = "src/test/resources/test-data.xlsx";

		List<Employee> employees = excelDataService.readExcel(testFilePath);
		assertEquals(2, employees.size());
	}

	@Test
	void testConvertToLong() {
		assertEquals(123L, excelDataService.convertToLong(123L));
		assertEquals(456L, excelDataService.convertToLong(456.789));
		assertEquals(0L, excelDataService.convertToLong(null));
		// Add more test cases as needed
	}

	@Test
	void testConvertToInt() {
		assertEquals(123, excelDataService.convertToInt(123));
		assertEquals(456, excelDataService.convertToInt(456.789));
		assertEquals(0, excelDataService.convertToInt(null));
	}
}
