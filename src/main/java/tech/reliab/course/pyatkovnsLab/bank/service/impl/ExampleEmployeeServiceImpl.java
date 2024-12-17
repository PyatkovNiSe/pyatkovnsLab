package tech.reliab.course.pyatkovnsLab.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;
import tech.reliab.course.pyatkovnsLab.bank.model.EmployeeRequest;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankOfficeRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.EmployeeRepository;
import tech.reliab.course.pyatkovnsLab.bank.service.EmployeeServiceImpl;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleEmployeeServiceImpl implements EmployeeServiceImpl {
    private final EmployeeRepository employeeRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.save(
                new Employee(
                        employeeRequest.getFullName(),
                        employeeRequest.getBirthDate(),
                        employeeRequest.getPosition(),
                        bankRepository.findById(employeeRequest.getBankId()).orElseThrow(),
                        employeeRequest.isRemoteWork(),
                        bankOfficeRepository.findById(employeeRequest.getBankOfficeId()).orElseThrow(),
                        employeeRequest.isCanIssueLoans(),
                        employeeRequest.getSalary()
                )
        );
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(int id, String name) {
        var employee = employeeRepository.findById(id).orElseThrow();
        employee.setFullName(name);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeDtoById(int id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
