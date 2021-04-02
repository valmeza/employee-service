package com.employeeservcie.controller;

import com.employeeservcie.feignService.EmployeeServiceFeign;
import com.employeeservcie.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerIT {

    MockMvc mockMvc;

    Employee employee;

    @SpyBean
    EmployeeController employeeController;

    @MockBean
    EmployeeServiceFeign employeeServiceFeign;

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employee = new Employee(null, "val", "meza", "email");
    }

    @Test
    public void getAllEmployees_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/api/employees")).andExpect(status().isOk());
    }

    @Test
    public void getAllEmployees_ShouldCallEmployeeService() throws Exception {
        mockMvc.perform(get("/api/employees")).andExpect(status().isOk());
        Mockito.verify(employeeServiceFeign).getEmployees();
    }

    @Test
    public void getEmployeeById_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk());
    }

    @Test
    public void getEmployeeById_ShouldCallEmployeeService() throws Exception {
        mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk());
        Mockito.verify(employeeServiceFeign).getEmployee(1L);
    }

    @Test
    public void saveEmployee_ShouldReturnStatus201() throws Exception {
        String testBody = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testBody)
        ).andExpect(status().isCreated());
    }

    @Test
    public void saveEmployee_ShouldCallEmployeeService() throws Exception {
        String testBody = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testBody)
        ).andExpect(status().isCreated());
        Mockito.verify(employeeServiceFeign).save(employeeArgumentCaptor.capture());
        assertEquals(employee.getId(), employeeArgumentCaptor.getValue().getId());
    }

    @Test
    public void  updateEmployee_ShouldReturnStatus200() throws Exception {
        employee.setId(1L);
        String testBody = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(put("/api/employees/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testBody)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateEmployee_ShouldCallEmployeeService() throws Exception {
        employee.setId(1L);
        String testBody = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(put("/api/employees/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testBody)
        ).andExpect(status().isOk());

        Mockito.verify(employeeServiceFeign).update(employeeArgumentCaptor.capture());
        assertEquals(employee.getId(), employeeArgumentCaptor.getValue().getId());
    }

    @Test
    public void deleteEmployee_ShouldReturn204Status() throws Exception {
        mockMvc.perform(delete("/api/employees/1")).andExpect(status().isNoContent());
        Mockito.verify(employeeServiceFeign).delete(1L);
    }
}