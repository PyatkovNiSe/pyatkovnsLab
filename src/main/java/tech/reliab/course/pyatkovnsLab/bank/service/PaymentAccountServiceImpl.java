package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.PaymentAccount;
import tech.reliab.course.pyatkovnsLab.bank.model.PaymentAccountRequest;

import java.util.List;

public interface PaymentAccountServiceImpl {
    PaymentAccount createPaymentAccount(PaymentAccountRequest paymentAccountRequest);

    void deletePaymentAccount(int id);

    PaymentAccount updatePaymentAccount(int id, int bankId);

    PaymentAccount getPaymentAccountDtoById(int id);

    List<PaymentAccount> getAllPaymentAccounts();
}
