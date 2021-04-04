package com.employeeservcie.feignService;

import com.employeeservcie.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "employee-service", url = "${feignClient.url}")
public interface EmployeeServiceFeign {

    @GetMapping
    List<Employee> getEmployees();

    @GetMapping("/{id}")
    Employee getEmployee(@PathVariable long id);

    @PostMapping
    Employee save(@RequestBody Employee employee);

    @PutMapping("/update")
    Employee update(@RequestBody Employee employee);

    @DeleteMapping("{id}")
    void delete(@PathVariable long id);
}
