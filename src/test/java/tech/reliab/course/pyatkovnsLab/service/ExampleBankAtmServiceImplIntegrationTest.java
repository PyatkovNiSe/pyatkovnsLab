package tech.reliab.course.pyatkovnsLab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;
import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;
import tech.reliab.course.pyatkovnsLab.bank.entity.BankAtm;
import tech.reliab.course.pyatkovnsLab.bank.entity.BankOffice;
import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;
import tech.reliab.course.pyatkovnsLab.bank.entity.User;
import tech.reliab.course.pyatkovnsLab.bank.model.BankAtmRequest;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankAtmRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankOfficeRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.EmployeeRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.UserRepository;
import tech.reliab.course.pyatkovnsLab.bank.service.impl.ExampleBankAtmServiceImpl;
import tech.reliab.course.pyatkovnsLab.bank.PyatkovnsLabApplication;
import tech.reliab.course.pyatkovnsLab.container.PostgreSQLContainerConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PyatkovnsLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {PostgreSQLContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleBankAtmServiceImplIntegrationTest {

    @Autowired
    private ExampleBankAtmServiceImpl exampleBankAtmService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankAtmRepository bankAtmRepository;

    @Autowired
    private BankOfficeRepository bankOfficeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        bankAtmRepository.deleteAll();
        bankOfficeRepository.deleteAll();
        employeeRepository.deleteAll();
        userRepository.deleteAll();
        bankRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testRequestBankInfo() {
        // Проверка: банки успешно созданы
        List<Bank> banks = bankRepository.findAll();
        assertEquals(5, banks.size(), "Количество банков должно быть 5");

        // Проверка: банкоматы и офисы привязаны к банкам
        Bank testBank = banks.getFirst();
        assertFalse(bankAtmRepository.findAllByBankId(testBank.getId()).isEmpty(), "Банкоматы должны быть созданы");
        assertFalse(bankOfficeRepository.findAllByBankId(testBank.getId()).isEmpty(), "Офисы должны быть созданы");

        // Проверка работы метода requestBankInfo
        exampleBankAtmService.requestBankInfo();
    }

    @Test
    @Order(2)
    void testInitializeBankInfo() {
        String bankName = "Тестовый банк";
        exampleBankAtmService.createBankAtm(new BankAtmRequest(bankName, "bimbim", 1, 1, 1, true, true, 1));

        Bank bank = bankRepository.findByName(bankName).orElseThrow(() -> new AssertionError("Банк не найден"));
        assertEquals(bankName, bank.getName(), "Название банка не совпадает");

        List<BankAtm> bankAtms = bankAtmRepository.findAllByBankId(bank.getId());
        List<BankOffice> bankOffices = bankOfficeRepository.findAllByBankId(bank.getId());
        List<Employee> employees = employeeRepository.findAllByBankId(bank.getId());
        List<User> users = userRepository.findAllByBanksId(bank.getId());

        assertEquals(5, bankAtms.size(), "Банкоматов должно быть 5");
        assertEquals(5, bankOffices.size(), "Офисов должно быть 5");
        assertEquals(5, employees.size(), "Сотрудников должно быть 5");
        assertTrue(users.size() >= 3, "Должно быть как минимум 3 пользователя");
    }
}
