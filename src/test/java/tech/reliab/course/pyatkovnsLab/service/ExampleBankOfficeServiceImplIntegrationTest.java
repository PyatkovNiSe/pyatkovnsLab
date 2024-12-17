package tech.reliab.course.pyatkovnsLab.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.pyatkovnsLab.bank.PyatkovnsLabApplication;
import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;
import tech.reliab.course.pyatkovnsLab.bank.entity.BankOffice;
import tech.reliab.course.pyatkovnsLab.bank.model.BankOfficeRequest;
import tech.reliab.course.pyatkovnsLab.bank.repository.BankRepository;
import tech.reliab.course.pyatkovnsLab.bank.service.BankOfficeServiceImpl;
import tech.reliab.course.pyatkovnsLab.container.PostgreSQLContainerConfig;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = PyatkovnsLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {PostgreSQLContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleBankOfficeServiceImplIntegrationTest {

    @Autowired
    private BankOfficeServiceImpl bankOfficeServiceImpl;

    @Autowired
    private BankRepository bankRepository;

    private Bank testBank;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Test Bank", "Test Bank Address", 50000, 50, 20, 5));
    }

    @Test
    void testCreateBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Main Office");
        request.setAddress("Main Street 1");
        request.setCanPlaceAtm(true);
        request.setCanIssueLoan(true);
        request.setCashWithdrawal(true);
        request.setCashDeposit(false);
        request.setRentCost(1000);
        request.setBankId(testBank.getId());

        BankOffice createdOffice = bankOfficeServiceImpl.createBankOffice(request);
        Assertions.assertNotNull(createdOffice.getId(), "ID офиса должен быть не null после сохранения");
        Assertions.assertEquals("Main Office", createdOffice.getName(), "Имя офиса должно соответствовать установленному");
        Assertions.assertEquals("Main Street 1", createdOffice.getAddress(), "Адрес офиса должен соответствовать установленному");
        Assertions.assertTrue(createdOffice.isCanPlaceAtm(), "Офис должен поддерживать размещение банкоматов");
        Assertions.assertTrue(createdOffice.isCanIssueLoan(), "Офис должен выдавать кредиты");
        Assertions.assertTrue(createdOffice.isCashWithdrawal(), "Офис должен поддерживать выдачу наличных");
        Assertions.assertFalse(createdOffice.isCashDeposit(), "Офис не должен поддерживать приём наличных");
        Assertions.assertEquals(new BigDecimal("1000"), createdOffice.getRentCost(), "Стоимость аренды должна совпадать");
        Assertions.assertEquals(testBank.getId(), createdOffice.getBank().getId(), "Банк офиса должен совпадать с ранее созданным");
    }

    @Test
    void testUpdateBankOffice() {
        // Сначала создадим офис
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Branch Office");
        request.setAddress("Branch Street 5");
        request.setCanPlaceAtm(false);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(false);
        request.setCashDeposit(true);
        request.setRentCost(500);
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeServiceImpl.createBankOffice(request);
        // Обновим его имя
        BankOffice updatedOffice = bankOfficeServiceImpl.updateBankOffice(office.getId(), "New Branch Office");
        Assertions.assertEquals("New Branch Office", updatedOffice.getName(), "Имя офиса должно быть обновлено");
    }

    @Test
    void testGetBankOfficeById() {
        // Создаём офис
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Info Office");
        request.setAddress("Info Street 10");
        request.setCanPlaceAtm(true);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(true);
        request.setCashDeposit(true);
        request.setRentCost(2000);
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeServiceImpl.createBankOffice(request);
        BankOffice found = bankOfficeServiceImpl.getBankDtoOfficeById(office.getId());
        Assertions.assertNotNull(found, "Офис должен быть найден по ID");
        Assertions.assertEquals("Info Office", found.getName(), "Имя найденного офиса должно совпадать");
    }

    @Test
    void testGetAllBankOffices() {
        // Создадим несколько офисов
        BankOfficeRequest request1 = new BankOfficeRequest();
        request1.setName("Office A");
        request1.setAddress("Address A");
        request1.setCanPlaceAtm(true);
        request1.setCanIssueLoan(true);
        request1.setCashWithdrawal(true);
        request1.setCashDeposit(true);
        request1.setRentCost(1500);
        request1.setBankId(testBank.getId());
        bankOfficeServiceImpl.createBankOffice(request1);

        BankOfficeRequest request2 = new BankOfficeRequest();
        request2.setName("Office B");
        request2.setAddress("Address B");
        request2.setCanPlaceAtm(false);
        request2.setCanIssueLoan(false);
        request2.setCashWithdrawal(false);
        request2.setCashDeposit(false);
        request2.setRentCost(3000);
        request2.setBankId(testBank.getId());
        bankOfficeServiceImpl.createBankOffice(request2);

        List<BankOffice> offices = bankOfficeServiceImpl.getAllBankOffices();
        Assertions.assertEquals(2, offices.size(), "Должно вернуться 2 офиса");
    }

    @Test
    void testDeleteBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Office to Delete");
        request.setAddress("Delete Street 1");
        request.setCanPlaceAtm(false);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(false);
        request.setCashDeposit(false);
        request.setRentCost(700);
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeServiceImpl.createBankOffice(request);

        bankOfficeServiceImpl.deleteBankAtm(office.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> bankOfficeServiceImpl.getBankDtoOfficeById(office.getId()),
                "После удаления при попытке получить офис по ID должно выбрасываться исключение"
        );
    }
}
