
package atm.admin;

import atm.model.BankAccount;
import atm.service.AccountService;
import atm.util.FileManager;
import atm.util.InputValidator;

import java.util.Scanner;

public class AdminService {

    // Demonstration credentials only.
    private static final String ADMIN_ID = "IronMan";
    private static final String ADMIN_PASSWORD = "LoveU300";

    private final AccountService accountService;
    private final FileManager fileManager;
    private final String dataFile;
    private final Scanner scanner;

    public AdminService(
            AccountService accountService,
            FileManager fileManager,
            String dataFile,
            Scanner scanner) {

        this.accountService = accountService;
        this.fileManager = fileManager;
        this.dataFile = dataFile;
        this.scanner = scanner;
    }

    public void login() {
        String id = InputValidator.readNonBlank(
                scanner, "Admin ID: "
        );

        String password = InputValidator.readNonBlank(
                scanner, "Admin password: "
        );

        if (!ADMIN_ID.equals(id)
                || !ADMIN_PASSWORD.equals(password)) {

            System.out.println("Invalid admin credentials.");
            return;
        }

        System.out.println("Admin login successful.");

        boolean running = true;

        while (running) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. List all accounts");
            System.out.println("2. Search account");
            System.out.println("3. Activate/deactivate account");
            System.out.println("4. Unlock account");
            System.out.println("5. View transaction log");
            System.out.println("6. Logout");

            int choice = InputValidator.readInt(
                    scanner, "Select option: ", 1, 6
            );

            switch (choice) {
                case 1:
                    accountService.getAllAccounts()
                            .forEach(System.out::println);
                    break;

                case 2:
                    searchAccount();
                    break;

                case 3:
                    toggleAccount();
                    break;

                case 4:
                    unlockAccount();
                    break;

                case 5:
                    System.out.println(
                            "Transaction data file: "
                                    + dataFile.replace(
                                            ".ser",
                                            "-transactions.ser"
                                    )
                    );
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("Admin logged out.");
    }

    private void searchAccount() {
        String number = InputValidator.readNonBlank(
                scanner, "Account number: "
        );

        accountService.findAccount(number).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Account not found.")
        );
    }

    private void toggleAccount() {
        String number = InputValidator.readNonBlank(
                scanner, "Account number: "
        );

        BankAccount account = accountService
                .findAccount(number)
                .orElse(null);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.setActive(!account.isActive());

        save();
        System.out.println(
                "Account active status: " + account.isActive()
        );
    }

    private void unlockAccount() {
        String number = InputValidator.readNonBlank(
                scanner, "Account number: "
        );

        BankAccount account = accountService
                .findAccount(number)
                .orElse(null);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.setLocked(false);

        save();
        System.out.println("Account unlocked.");
    }

    private void save() {
        fileManager.saveAccounts(
                dataFile,
                accountService.getAllAccounts()
        );
    }
}
