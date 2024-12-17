package tech.reliab.course.pyatkovnsLab.bank;

import lombok.Getter;
import tech.reliab.course.pyatkovnsLab.bank.repository.*;
import tech.reliab.course.pyatkovnsLab.bank.repository.impl.*;
import tech.reliab.course.pyatkovnsLab.bank.service.BankAtmService;
import tech.reliab.course.pyatkovnsLab.bank.service.UserService;
import tech.reliab.course.pyatkovnsLab.bank.service.impl.BankAtmServiceImpl;
import tech.reliab.course.pyatkovnsLab.bank.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Scanner;

public class pyatkovnsLabApplication {
    private static final UserRepository userRepository = new UserRepositoryImpl();
    private static final BankRepository bankRepository = new BankRepositoryImpl(userRepository);
    private static final BankOfficeRepository bankOfficeRepository = new BankOfficeRepositoryImpl(bankRepository);
    private static final EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(bankRepository);
    private static final BankAtmRepository bankAtmRepository = new BankAtmRepositoryImpl(bankRepository);
    private static final PaymentAccountRepository paymentAccountRepository = new PaymentAccountRepositoryImpl(userRepository, bankRepository);
    private static final CreditAccountRepository creditAccountRepository = new CreditAccountRepositoryImpl(userRepository);

    private static final BankAtmService bankAtmService = new BankAtmServiceImpl(
            userRepository,
            bankRepository,
            bankOfficeRepository,
            employeeRepository,
            bankAtmRepository,
            paymentAccountRepository,
            creditAccountRepository
    );
    private static final UserService userService = new UserServiceImpl(
            userRepository,
            creditAccountRepository,
            paymentAccountRepository
    );

    private static MenuOption menuOption;

    public static void main(String[] args) throws IOException {
        bankAtmService.initializeBanks();

        System.out.println(bankRepository.getAllBanks());
        while (SelectMenuOption()) {
            switch (menuOption) {
                case BANK_INFO:
                    bankAtmService.requestBankInfo();
                    break;
                case USER_INFO:
                    userService.requestUserInfo();
                    break;
            }

        }
    }

    private static boolean SelectMenuOption() {
        var scanner = new Scanner(System.in);
        System.out.println("Выберите действие:");
        System.out.println("1. Вывести информацию о банке");
        System.out.println("2. Вывести информацию о пользователе");
        System.out.println("3. Выйти");
        menuOption = MenuOption.valueOf(scanner.nextInt());
        return menuOption != MenuOption.EXIT;
    }

    @Getter
    static enum MenuOption {
        BANK_INFO(1),
        USER_INFO(2),
        EXIT(3);

        private final int value;

        MenuOption(int value) {
            this.value = value;
        }

        public static MenuOption valueOf(int value) {
            for (MenuOption menuOption : values()) {
                if (menuOption.value == value) {
                    return menuOption;
                }
            }
            throw new IllegalArgumentException("No such option");
        }
    }
}
