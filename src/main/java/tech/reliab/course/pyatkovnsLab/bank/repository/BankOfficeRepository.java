package tech.reliab.course.pyatkovnsLab.bank.repository;

import tech.reliab.course.pyatkovnsLab.bank.entity.Bank;
import tech.reliab.course.pyatkovnsLab.bank.entity.BankOffice;

import java.util.List;
import java.util.Optional;

public interface BankOfficeRepository {
    BankOffice createBankOffice(String name, String address, boolean canPlaceAtm,
                                boolean canIssueLoan, boolean cashWithdrawal, boolean cashDeposit,
                                double rentCost, Bank bank);

    Optional<BankOffice> getBankOfficeById(int id);

    List<BankOffice> getAllBankOffices();

    List<BankOffice>  getAllBankOfficesByBank(Bank bank);

    void updateBankOffice(int id, String name);

    void deleteBankAtm(int officeId, int bankId);
}
