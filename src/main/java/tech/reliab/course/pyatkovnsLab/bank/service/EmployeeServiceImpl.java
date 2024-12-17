package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;
import tech.reliab.course.pyatkovnsLab.bank.model.EmployeeRequest;

import java.util.List;

public interface EmployeeServiceImpl {
    Employee createEmployee(EmployeeRequest employeeRequest);

    void deleteEmployee(int id);

    Employee updateEmployee(int id, String name);

    Employee getEmployeeDtoById(int id);

    List<Employee> getAllEmployees();
}
