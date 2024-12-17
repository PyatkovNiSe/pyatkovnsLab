package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.CreditAccount;
import tech.reliab.course.pyatkovnsLab.bank.model.CreditAccountRequest;

import java.util.List;

public interface CreditAccountServiceImpl {
    CreditAccount createCreditAccount(CreditAccountRequest creditAccountRequest);

    void deleteCreditAccount(int id);

    CreditAccount updateCreditAccount(int id, int bankId);

    CreditAccount getCreditAccountDtoById(int id);

    List<CreditAccount> getAllCreditAccounts();
}
