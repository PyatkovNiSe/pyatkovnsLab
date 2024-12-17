package tech.reliab.course.pyatkovnsLab.bank.repository;

import tech.reliab.course.pyatkovnsLab.bank.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditAccountRepository {
    CreditAccount createCreditAccount(User user, Bank bank, LocalDate startDate, int loanTermMonths,
                                      double loanAmount, double interestRate, Employee employee,
                                      PaymentAccount paymentAccount);

    Optional<CreditAccount> getCreditAccountById(int id);

    List<CreditAccount> getCreditAccountByUserId(int userId);

    List<CreditAccount> getAllCreditAccounts();

    void updateCreditAccount(int id, Bank bank);

    void deleteCreditAccount(int accountId, int userId);
}