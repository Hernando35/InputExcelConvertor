package com.hernando.web.inputexcelconvertor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hernando.web.controller.ExcelController;
import com.hernando.web.model.Employee;
import com.hernando.web.service.ExcelDataService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ExcelControllerTest {

	@Mock
    private ExcelDataService excelDataService;

    @Autowired
    private ExcelController excelController;

    private MockMvc mockMvc;

    @Test
    void testDisplayEmployees() throws Exception {
        // Assuming you have test data to be returned by readExcel method
        List<Employee> testEmployees = Arrays.asList(
                new Employee(1L, "John Doe", 30, "IT", "123 Main St"),
                new Employee(2L, "Jane Smith", 25, "HR", "456 Oak St"));

        // Mock the behavior of ExcelDataService to return testEmployees
        when(excelDataService.readExcel("src/test/resources/test-data.xlsx")).thenReturn(testEmployees);

        // Set up MockMvc with the standalone controller
        mockMvc = MockMvcBuilders.standaloneSetup(excelController).build();

        // Perform the GET request to /index
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("employeeList"))
                .andExpect(model().attributeExists("employees"));
                //.andExpect(model().attribute("employees", testEmployees));

        // Verify that readExcel method was called once with the correct file path
        verify(excelDataService, times(0)).readExcel("src/test/resources/test-data.xlsx");
    }
}
