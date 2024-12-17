package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;

import java.util.List;

public interface BankServiceImpl {
    Bank createBank(String bankName);

    void deleteBank(int id);

    Bank updateBank(int id, String bankName);

    List<Bank> getAllBanks();

    Bank getBankDtoById(int id);
}
