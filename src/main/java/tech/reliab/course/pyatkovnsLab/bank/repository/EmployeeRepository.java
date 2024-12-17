package tech.reliab.course.pyatkovnsLab.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findById(int id);

    void deleteById(int id);

    List<Employee> findAllByBankId(int bankId);
}
