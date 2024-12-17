package tech.reliab.course.pyatkovnsLab.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.pyatkovnsLab.bank.PyatkovnsLabApplication;
import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;
import tech.reliab.course.pyatkovnsLab.bank.service.BankServiceImpl;
import tech.reliab.course.pyatkovnsLab.container.PostgreSQLContainerConfig;

import java.util.List;

@SpringBootTest(classes = PyatkovnsLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {PostgreSQLContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleBankServiceImplIntegrationTest {

    @Autowired
    private BankServiceImpl bankServiceImpl;

    @Test
    void testCreateBank() {
        Bank createdBank = bankServiceImpl.createBank("Test Bank");
        Assertions.assertNotNull(createdBank.getId(), "ID банка должен быть не null после сохранения");
        Assertions.assertEquals("Test Bank", createdBank.getName(), "Имя банка должно совпадать с заданным");
    }

    @Test
    void testUpdateBank() {
        Bank createdBank = bankServiceImpl.createBank("Old Name");
        Bank updatedBank = bankServiceImpl.updateBank(createdBank.getId(), "New Name");
        Assertions.assertEquals("New Name", updatedBank.getName(), "Название банка должно быть обновлено");
    }

    @Test
    void testGetBankById() {
        Bank createdBank = bankServiceImpl.createBank("Unique Bank");
        Bank foundBank = bankServiceImpl.getBankDtoById(createdBank.getId());
        Assertions.assertEquals("Unique Bank", foundBank.getName(), "Полученный по ID банк должен совпадать");
    }

    @Test
    void testGetAllBanks() {
        bankServiceImpl.createBank("Bank A");
        bankServiceImpl.createBank("Bank B");
        List<Bank> banks = bankServiceImpl.getAllBanks();
        Assertions.assertEquals(2, banks.size(), "Должно вернуться 2 банка");
    }

    @Test
    void testDeleteBank() {
        Bank createdBank = bankServiceImpl.createBank("Bank to Delete");
        bankServiceImpl.deleteBank(createdBank.getId());
        Assertions.assertThrows(
                RuntimeException.class,
                () -> bankServiceImpl.getBankDtoById(createdBank.getId()),
                "После удаления при попытке получить банк по ID должно быть исключение"
        );
    }
}