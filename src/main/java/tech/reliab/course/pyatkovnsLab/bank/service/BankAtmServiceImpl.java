package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.BankAtm;
import tech.reliab.course.pyatkovnsLab.bank.model.BankAtmRequest;

import java.util.List;

public interface BankAtmServiceImpl {
    void requestBankInfo();

    BankAtm createBankAtm(BankAtmRequest bankAtmRequest);

    void deleteBankAtm(int id);

    BankAtm updateBankAtm(int id, String name);

    BankAtm getBankAtmDtoById(int id);

    List<BankAtm> getAllBankAtmsByBankId(int bankId);

    List<BankAtm> getAllBankAtms();
}
