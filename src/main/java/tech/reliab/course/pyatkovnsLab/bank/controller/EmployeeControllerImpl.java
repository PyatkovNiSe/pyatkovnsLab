package tech.reliab.course.pyatkovnsLab.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;
import tech.reliab.course.pyatkovnsLab.bank.model.EmployeeRequest;

import java.util.List;

public interface EmployeeControllerImpl {

    ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest employeeRequest);

    ResponseEntity<Void> deleteEmployee(@PathVariable int id);

    ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestParam(name = "name") String name);

    ResponseEntity<Employee> getEmployeeById(@PathVariable int id);

    ResponseEntity<List<Employee>> getAllEmployees();
}
