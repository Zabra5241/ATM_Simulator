
package atm;

import atm.admin.AdminService;
import atm.model.BankAccount;
import atm.model.CurrentAccount;
import atm.model.Customer;
import atm.model.SavingsAccount;
import atm.service.ATMService;
import atm.service.AccountService;
import atm.service.AuthService;
import atm.util.FileManager;
import atm.util.InputValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final FileManager FILE_MANAGER =
            new FileManager();

    private static final String DATA_FILE =
            "data/atm-data.ser";

    public static void main(String[] args) {

        List<BankAccount> loadedAccounts =
                FILE_MANAGER.loadAccounts(DATA_FILE);

        AccountService accountService =
                new AccountService(loadedAccounts);

        AuthService authService =
                new AuthService(accountService);

        ATMService atmService =
                new ATMService(
                        accountService,
                        FILE_MANAGER,
                        DATA_FILE
                );

        AdminService adminService =
                new AdminService(
                        accountService,
                        FILE_MANAGER,
                        DATA_FILE,
                        SCANNER
                );

        System.out.println("==================================");
        System.out.println("       GS BANK ATM SIMULATOR");
        System.out.println("==================================");

        boolean running = true;

        while (running) {
            System.out.println("\n1. Customer Login");
            System.out.println("2. Create Bank Account");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

            int choice = InputValidator.readInt(
                    SCANNER, "Select option: ", 1, 4
            );

            switch (choice) {
                case 1:
                    customerLogin(authService, atmService);
                    break;

                case 2:
                    createAccount(accountService, atmService);
                    break;

                case 3:
                    adminService.login();
                    break;

                case 4:
                    atmService.persist();
                    System.out.println(
                            "Thank you for using GS Bank ATM."
                    );
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        SCANNER.close();
    }

    private static void createAccount(
            AccountService accountService,
            ATMService atmService) {

        System.out.println("\n--- CREATE ACCOUNT ---");

        String name = InputValidator.readNonBlank(
                SCANNER, "Full name: "
        );

        String phone = InputValidator.readNonBlank(
                SCANNER, "Phone number: "
        );

        String pin = InputValidator.readPin(
                SCANNER, "Set a 4-digit PIN: "
        );

        String confirmPin = InputValidator.readPin(
                SCANNER, "Confirm PIN: "
        );

        if (!pin.equals(confirmPin)) {
            System.out.println("PINs do not match.");
            return;
        }

        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");

        int type = InputValidator.readInt(
                SCANNER, "Account type: ", 1, 2
        );

        BigDecimal openingBalance =
                InputValidator.readMoney(
                        SCANNER,
                        "Opening deposit: ",
                        true
                );

        String accountNumber =
                accountService.generateAccountNumber();

        Customer customer = new Customer(
                name,
                phone,
                AuthService.hashPin(pin)
        );

        BankAccount account;

        if (type == 1) {
            account = new SavingsAccount(
                    accountNumber,
                    customer,
                    openingBalance
            );
        } else {
            account = new CurrentAccount(
                    accountNumber,
                    customer,
                    openingBalance
            );
        }

        accountService.addAccount(account);
        atmService.persist();

        System.out.println("\nAccount created successfully!");
        System.out.println(
                "Account number: " + accountNumber
        );
        System.out.println(
                "Remember your account number. "
                        + "Never share your PIN."
        );
    }

    private static void customerLogin(
            AuthService authService,
            ATMService atmService) {

        String accountNumber = InputValidator.readNonBlank(
                SCANNER, "Account number: "
        );

        String pin = InputValidator.readPin(
                SCANNER, "4-digit PIN: "
        );

        BankAccount account =
                authService.authenticate(accountNumber, pin);

        // Persist failed PIN attempts and account lockouts.
        atmService.persist();

        if (account == null) {
            System.out.println(
                    "Login failed. Check your details or account status."
            );
            return;
        }

        System.out.println(
                "\nWelcome, " + account.getCustomer().getName()
        );

        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Transfer Money");
            System.out.println("5. Mini Statement");
            System.out.println("6. Change PIN");
            System.out.println("7. Logout");

            int choice = InputValidator.readInt(
                    SCANNER, "Select option: ", 1, 7
            );

            try {
                switch (choice) {
                    case 1:
                        System.out.println(
                                "Balance: " + account.getBalance()
                        );
                        break;

                    case 2:
                        BigDecimal withdrawal =
                                InputValidator.readMoney(
                                        SCANNER,
                                        "Withdrawal amount: ",
                                        false
                                );

                        atmService.withdraw(
                                account, withdrawal
                        );

                        System.out.println(
                                "Withdrawal successful."
                        );
                        System.out.println(
                                "Balance: " + account.getBalance()
                        );
                        break;

                    case 3:
                        BigDecimal deposit =
                                InputValidator.readMoney(
                                        SCANNER,
                                        "Deposit amount: ",
                                        false
                                );

                        atmService.deposit(account, deposit);

                        System.out.println(
                                "Deposit successful."
                        );
                        System.out.println(
                                "Balance: " + account.getBalance()
                        );
                        break;

                    case 4:
                        String recipient =
                                InputValidator.readNonBlank(
                                        SCANNER,
                                        "Recipient account number: "
                                );

                        BigDecimal transferAmount =
                                InputValidator.readMoney(
                                        SCANNER,
                                        "Transfer amount: ",
                                        false
                                );

                        atmService.transfer(
                                account,
                                recipient,
                                transferAmount
                        );

                        System.out.println(
                                "Transfer successful."
                        );
                        break;

                    case 5:
                        atmService.printMiniStatement(account);
                        break;

                    case 6:
                        changePin(authService, atmService, account);
                        break;

                    case 7:
                        loggedIn = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (Exception ex) {
                System.out.println(
                        "Operation failed: " + ex.getMessage()
                );
            }
        }

        atmService.persist();
        System.out.println("You have been logged out.");
    }

    private static void changePin(
            AuthService authService,
            ATMService atmService,
            BankAccount account) {

        String oldPin = InputValidator.readPin(
                SCANNER, "Current PIN: "
        );

        String newPin = InputValidator.readPin(
                SCANNER, "New PIN: "
        );

        String confirmPin = InputValidator.readPin(
                SCANNER, "Confirm new PIN: "
        );

        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match.");
            return;
        }

        if (oldPin.equals(newPin)) {
            System.out.println(
                    "New PIN must differ from the current PIN."
            );
            return;
        }

        if (authService.changePin(account, oldPin, newPin)) {
            atmService.persist();
            System.out.println("PIN changed successfully.");
        } else {
            System.out.println("Current PIN is incorrect.");
        }
    }
}
