package tech.reliab.course.pyatkovnsLab.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.pyatkovnsLab.bank.entity.BankOffice;
import tech.reliab.course.pyatkovnsLab.bank.model.BankOfficeRequest;
import tech.reliab.course.pyatkovnsLab.bank.service.BankOfficeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-offices")
public class BankOfficeControllerImplImpl implements tech.reliab.course.pyatkovnsLab.bank.controller.BankOfficeControllerImpl {

    private final BankOfficeServiceImpl bankOfficeServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<BankOffice> createBankOffice(BankOfficeRequest bankOfficeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankOfficeServiceImpl.createBankOffice(bankOfficeRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankOffice(int id) {
        bankOfficeServiceImpl.deleteBankAtm(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<BankOffice> updateBankOffice(int id, String name) {
        return ResponseEntity.ok(bankOfficeServiceImpl.updateBankOffice(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BankOffice> getBankOfficeById(int id) {
        return ResponseEntity.ok(bankOfficeServiceImpl.getBankDtoOfficeById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BankOffice>> getAllBankOffices() {
        return ResponseEntity.ok(bankOfficeServiceImpl.getAllBankOffices());
    }
}
