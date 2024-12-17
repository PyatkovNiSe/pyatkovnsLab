package tech.reliab.course.pyatkovnsLab.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;
import tech.reliab.course.pyatkovnsLab.bank.model.EmployeeRequest;
import tech.reliab.course.pyatkovnsLab.bank.service.EmployeeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeControllerImplImpl implements tech.reliab.course.pyatkovnsLab.bank.controller.EmployeeControllerImpl {

    private final EmployeeServiceImpl employeeServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<Employee> createEmployee(EmployeeRequest employeeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeServiceImpl.createEmployee(employeeRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(int id) {
        employeeServiceImpl.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(int id, String name) {
        return ResponseEntity.ok(employeeServiceImpl.updateEmployee(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(int id) {
        return ResponseEntity.ok(employeeServiceImpl.getEmployeeDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeServiceImpl.getAllEmployees());
    }
}
