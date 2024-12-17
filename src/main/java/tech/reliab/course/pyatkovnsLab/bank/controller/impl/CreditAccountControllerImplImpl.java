package tech.reliab.course.pyatkovnsLab.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.pyatkovnsLab.bank.entity.CreditAccount;
import tech.reliab.course.pyatkovnsLab.bank.model.CreditAccountRequest;
import tech.reliab.course.pyatkovnsLab.bank.service.CreditAccountServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit-accounts")
public class CreditAccountControllerImplImpl implements tech.reliab.course.pyatkovnsLab.bank.controller.CreditAccountControllerImpl {

    private final CreditAccountServiceImpl creditAccountServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<CreditAccount> createCreditAccount(CreditAccountRequest creditAccountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(creditAccountServiceImpl.createCreditAccount(creditAccountRequest));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteCreditAccount(int id) {
        creditAccountServiceImpl.deleteCreditAccount(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<CreditAccount> updateCreditAccount(int id, int bankId) {
        return ResponseEntity.ok(creditAccountServiceImpl.updateCreditAccount(id, bankId));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CreditAccount> getBankByCreditAccount(int id) {
        return ResponseEntity.ok(creditAccountServiceImpl.getCreditAccountDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CreditAccount>> getAllCreditAccounts() {
        return ResponseEntity.ok(creditAccountServiceImpl.getAllCreditAccounts());
    }
}
