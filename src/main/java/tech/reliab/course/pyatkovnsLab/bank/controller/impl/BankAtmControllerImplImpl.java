package tech.reliab.course.pyatkovnsLab.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.pyatkovnsLab.bank.entity.BankAtm;
import tech.reliab.course.pyatkovnsLab.bank.model.BankAtmRequest;
import tech.reliab.course.pyatkovnsLab.bank.service.BankAtmServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-atms")
public class BankAtmControllerImplImpl implements tech.reliab.course.pyatkovnsLab.bank.controller.BankAtmControllerImpl {

    private final BankAtmServiceImpl bankAtmServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<BankAtm> createBankAtm(BankAtmRequest bankAtmRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAtmServiceImpl.createBankAtm(bankAtmRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAtm(int id) {
        bankAtmServiceImpl.deleteBankAtm(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<BankAtm> updateBankAtm(int id, String name) {
        return ResponseEntity.ok(bankAtmServiceImpl.updateBankAtm(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BankAtm> getBankAtmById(int id) {
        return ResponseEntity.ok(bankAtmServiceImpl.getBankAtmDtoById(id));
    }

    @Override
    @GetMapping("/all-by-bank/{bankId}")
    public ResponseEntity<List<BankAtm>> getAllBankAtmByBankId(int bankId) {
        return ResponseEntity.ok(bankAtmServiceImpl.getAllBankAtmsByBankId(bankId));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BankAtm>> getAllBankAtms() {
        return ResponseEntity.ok(bankAtmServiceImpl.getAllBankAtms());
    }
}
