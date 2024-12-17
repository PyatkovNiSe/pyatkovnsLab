package tech.reliab.course.pyatkovnsLab.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.pyatkovnsLab.bank.entity.CreditAccount;
import tech.reliab.course.pyatkovnsLab.bank.model.CreditAccountRequest;
import tech.reliab.course.pyatkovnsLab.bank.repository.*;
import tech.reliab.course.pyatkovnsLab.bank.service.CreditAccountServiceImpl;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleCreditAccountServiceImpl implements CreditAccountServiceImpl {
    private final CreditAccountRepository creditAccountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public CreditAccount createCreditAccount(CreditAccountRequest creditAccountRequest) {
        return creditAccountRepository.save(
                new CreditAccount(
                        userRepository.findById(creditAccountRequest.getUserId()).orElseThrow(),
                        bankRepository.findById(creditAccountRequest.getBankId()).orElseThrow(),
                        creditAccountRequest.getStartDate(),
                        creditAccountRequest.getLoanTermMonths(),
                        creditAccountRequest.getInterestRate(),
                        employeeRepository.findById(creditAccountRequest.getEmployeeId()).orElseThrow(),
                        paymentAccountRepository.findById(creditAccountRequest.getPaymentAccountId()).orElseThrow()
                )
        );
    }

    @Override
    public void deleteCreditAccount(int id) {
        creditAccountRepository.deleteById(id);
    }

    @Override
    public CreditAccount updateCreditAccount(int id, int bankId) {
        var creditAccount = creditAccountRepository.findById(id).orElseThrow();
        creditAccount.setBank(bankRepository.findById(bankId).orElseThrow());

        return creditAccountRepository.save(creditAccount);
    }

    @Override
    public CreditAccount getCreditAccountDtoById(int id) {
        return creditAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<CreditAccount> getAllCreditAccounts() {
        return creditAccountRepository.findAll();
    }
}
