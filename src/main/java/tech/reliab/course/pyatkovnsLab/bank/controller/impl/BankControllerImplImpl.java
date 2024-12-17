package tech.reliab.course.pyatkovnsLab.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;
import tech.reliab.course.pyatkovnsLab.bank.service.BankServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banks")
public class BankControllerImplImpl implements tech.reliab.course.pyatkovnsLab.bank.controller.BankControllerImpl {

    private final BankServiceImpl bankServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<Bank> createBank(String bankName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankServiceImpl.createBank(bankName));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(int id) {
        bankServiceImpl.deleteBank(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Bank> updateBank(int id, String bankName) {
        return ResponseEntity.ok(bankServiceImpl.updateBank(id, bankName));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(int id) {
        return ResponseEntity.ok(bankServiceImpl.getBankDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        return ResponseEntity.ok(bankServiceImpl.getAllBanks());
    }
}
