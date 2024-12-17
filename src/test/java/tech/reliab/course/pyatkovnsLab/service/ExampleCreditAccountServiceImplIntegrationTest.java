package tech.reliab.course.pyatkovnsLab.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.pyatkovnsLab.bank.PyatkovnsLabApplication;
import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;
import tech.reliab.course.pyatkovnsLab.bank.entity.CreditAccount;
import tech.reliab.course.pyatkovnsLab.bank.entity.Employee;
import tech.reliab.course.pyatkovnsLab.bank.entity.PaymentAccount;
import tech.reliab.course.pyatkovnsLab.bank.entity.User;
import tech.reliab.course.pyatkovnsLab.bank.model.CreditAccountRequest;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.EmployeeRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.PaymentAccountRepository;
import tech.reliab.course.pyatkovnsLab.bank.repository.UserRepository;
import tech.reliab.course.pyatkovnsLab.bank.service.CreditAccountServiceImpl;
import tech.reliab.course.pyatkovnsLab.container.PostgreSQLContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = PyatkovnsLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {PostgreSQLContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleCreditAccountServiceImplIntegrationTest {

    @Autowired
    private CreditAccountServiceImpl creditAccountServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    private User testUser;
    private Bank testBank;
    private Employee testEmployee;
    private PaymentAccount testPaymentAccount;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Test Bank"));
        testUser = userRepository.save(new User("John", "Doe", LocalDate.of(1990, 1, 1), testBank.getId()));
        testEmployee = employeeRepository.save(new Employee(testBank, "Jane", "Smith", "Loan Officer", true, true, 5000));
        testPaymentAccount = paymentAccountRepository.save(new PaymentAccount(testUser, 10000));
    }

    @Test
    void testCreateCreditAccount() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2022, 1, 1));
        request.setLoanTermMonths(12);
        request.setInterestRate(5.5);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountServiceImpl.createCreditAccount(request);
        Assertions.assertNotNull(createdAccount.getId(), "ID кредитного аккаунта должен быть не null");
        Assertions.assertEquals(testUser.getId(), createdAccount.getUser().getId(), "Пользователь должен совпадать");
        Assertions.assertEquals(testBank.getId(), createdAccount.getBank().getId(), "Банк должен совпадать");
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), createdAccount.getStartDate(), "Дата начала кредита должна совпадать");
        Assertions.assertEquals(12, createdAccount.getLoanTermMonths(), "Срок кредита должен быть 12 месяцев");
        Assertions.assertEquals(5.5, createdAccount.getInterestRate(), "Процентная ставка должна совпадать");
        Assertions.assertEquals(testEmployee.getId(), createdAccount.getEmployee().getId(), "Сотрудник должен совпадать");
        Assertions.assertEquals(testPaymentAccount.getId(), createdAccount.getPaymentAccount().getId(), "Платёжный аккаунт должен совпадать");
    }

    @Test
    void testUpdateCreditAccount() {
        // Создадим кредитный аккаунт
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2022, 2, 1));
        request.setLoanTermMonths(24);
        request.setInterestRate(6.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountServiceImpl.createCreditAccount(request);

        // Создадим новый банк, чтобы проверить обновление банка у кредитного аккаунта
        Bank newBank = bankRepository.save(new Bank("Another Bank"));
        CreditAccount updatedAccount = creditAccountServiceImpl.updateCreditAccount(createdAccount.getId(), newBank.getId());
        Assertions.assertEquals(newBank.getId(), updatedAccount.getBank().getId(), "Банк в кредитном аккаунте должен обновиться");
    }

    @Test
    void testGetCreditAccountById() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2023, 3, 10));
        request.setLoanTermMonths(36);
        request.setInterestRate(4.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountServiceImpl.createCreditAccount(request);

        CreditAccount found = creditAccountServiceImpl.getCreditAccountDtoById(createdAccount.getId());
        Assertions.assertNotNull(found, "Кредитный аккаунт должен быть найден по ID");
        Assertions.assertEquals(4.0, found.getInterestRate(), "Процентная ставка должна совпадать");
    }

    @Test
    void testGetAllCreditAccounts() {
        CreditAccountRequest request1 = new CreditAccountRequest();
        request1.setUserId(testUser.getId());
        request1.setBankId(testBank.getId());
        request1.setStartDate(LocalDate.of(2021, 5, 5));
        request1.setLoanTermMonths(6);
        request1.setInterestRate(7.0);
        request1.setEmployeeId(testEmployee.getId());
        request1.setPaymentAccountId(testPaymentAccount.getId());
        creditAccountServiceImpl.createCreditAccount(request1);

        CreditAccountRequest request2 = new CreditAccountRequest();
        request2.setUserId(testUser.getId());
        request2.setBankId(testBank.getId());
        request2.setStartDate(LocalDate.of(2021, 6, 6));
        request2.setLoanTermMonths(18);
        request2.setInterestRate(7.5);
        request2.setEmployeeId(testEmployee.getId());
        request2.setPaymentAccountId(testPaymentAccount.getId());
        creditAccountServiceImpl.createCreditAccount(request2);

        List<CreditAccount> accounts = creditAccountServiceImpl.getAllCreditAccounts();
        Assertions.assertEquals(2, accounts.size(), "Должно вернуться 2 кредитных аккаунта");
    }

    @Test
    void testDeleteCreditAccount() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2023, 4, 20));
        request.setLoanTermMonths(12);
        request.setInterestRate(5.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountServiceImpl.createCreditAccount(request);
        creditAccountServiceImpl.deleteCreditAccount(createdAccount.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> creditAccountServiceImpl.getCreditAccountDtoById(createdAccount.getId()),
                "После удаления попытка получения аккаунта по ID должна вызывать исключение"
        );
    }
}