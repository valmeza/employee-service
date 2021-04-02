package com.employeeservcie.controller;

import com.employeeservcie.feignService.EmployeeServiceFeign;
import com.employeeservcie.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeServiceFeign employeeServiceFeign;

    public EmployeeController(EmployeeServiceFeign employeeServiceFeign) {
        this.employeeServiceFeign = employeeServiceFeign;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeServiceFeign.getEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeServiceFeign.getEmployee(id);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(Employee employee) {
        return employeeServiceFeign.save(employee);
    }

    @PutMapping("/employees/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeServiceFeign.update(employee);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable long id) {
        employeeServiceFeign.delete(id);
    }
}
