package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.BankOffice;
import tech.reliab.course.pyatkovnsLab.bank.model.BankOfficeRequest;

import java.util.List;

public interface BankOfficeServiceImpl {

    BankOffice createBankOffice(BankOfficeRequest bankOfficeRequest);

    void deleteBankAtm(int id);

    BankOffice updateBankOffice(int id, String name);

    BankOffice getBankDtoOfficeById(int id);

    List<BankOffice> getAllBankOffices();
}
