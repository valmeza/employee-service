package com.employeeservcie.controller;

import com.employeeservcie.feignService.EmployeeServiceFeign;
import com.employeeservcie.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {

    private EmployeeController employeeController;
    private EmployeeServiceFeign employeeServiceFeign;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employeeServiceFeign = Mockito.mock(EmployeeServiceFeign.class);
        employeeController = new EmployeeController(employeeServiceFeign);
        employee = new Employee(null, "Val", "Meza", "email");
    }

    @Test
    public void getAllEmployees_ShouldReturnEmptyListIfEmpty() {
        // arrange
        List<Employee> expected = new ArrayList<>();
        Mockito.when(employeeServiceFeign.getEmployees()).thenReturn(expected);

        // act
        List<Employee> response = employeeController.getAllEmployees();

        // assert
        assertEquals(expected, response);
    }

    @Test
    public void getAllEmployees_ShouldReturnListOfEmployees() {
        // arrange
        List<Employee> expected = new ArrayList<>();
        expected.add(new Employee(null, "Val", "Mez", "mail"));
        expected.add(new Employee(null, "Vale", "Meza", "mails"));
        Mockito.when(employeeServiceFeign.getEmployees()).thenReturn(expected);

        // act
        List<Employee> response = employeeController.getAllEmployees();

        // assert
        assertEquals(expected, response);
    }

    @Test
    public void getEmployeeById_ShouldReturnEmployee() {
        // arrange
        this.employee.setId(1L);
        Employee expected = this.employee;
        Mockito.when(employeeServiceFeign.getEmployee(1L)).thenReturn(expected);

        // act
        Employee response = employeeController.getEmployeeById(1L);

        // assert
        assertEquals(expected, response);
    }

    @Test
    public void saveEmployee_ShouldReturnEmployeeWithId() {
        // arrange
        Employee postBodyInput = this.employee;
        this.employee.setId(1L);
        Employee expected = this.employee;
        Mockito.when(employeeServiceFeign.save(postBodyInput)).thenReturn(expected);

        // act
        Employee response = employeeController.saveEmployee(postBodyInput);

        // assert
        assertEquals(expected, response);
    }

    @Test
    public void updateEmployee_ShouldReturnUpdatedEmployee() {
        // arrange
        this.employee.setId(1L);
        Employee postBodyInput = this.employee;
        Employee expected = this.employee;
        Mockito.when(employeeServiceFeign.update(postBodyInput)).thenReturn(expected);

        // act
        Employee response = employeeController.updateEmployee(postBodyInput);

        // assert
        assertEquals(expected, response);
    }

    @Test
    public void deleteEmployee_ShouldDeleteEmployee() {
        // arrange
        long id = 1L;

        // act
        employeeController.deleteEmployee(id);

        // assert
        Mockito.verify(employeeServiceFeign).delete(id);
    }
}