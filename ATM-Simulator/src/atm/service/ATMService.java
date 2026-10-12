
package atm.service;

import atm.model.BankAccount;
import atm.model.Transaction;
import atm.util.FileManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ATMService {

    private final AccountService accountService;
    private final FileManager fileManager;
    private final String dataFile;
    private final List<Transaction> transactions;

    public ATMService(
            AccountService accountService,
            FileManager fileManager,
            String dataFile) {

        this.accountService = accountService;
        this.fileManager = fileManager;
        this.dataFile = dataFile;

        this.transactions = new ArrayList<>(
                fileManager.loadTransactions(transactionFile())
        );
    }

    public synchronized void deposit(
            BankAccount account,
            BigDecimal amount) {

        requireUsable(account);
        account.deposit(amount);

        transactions.add(new Transaction(
                account.getAccountNumber(),
                Transaction.Type.DEPOSIT,
                amount,
                ""
        ));

        persist();
    }

    public synchronized void withdraw(
            BankAccount account,
            BigDecimal amount) {

        requireUsable(account);
        account.withdraw(amount);

        transactions.add(new Transaction(
                account.getAccountNumber(),
                Transaction.Type.WITHDRAWAL,
                amount,
                ""
        ));

        persist();
    }

    public synchronized void transfer(
            BankAccount sender,
            String recipientNumber,
            BigDecimal amount) {

        requireUsable(sender);

        BankAccount recipient = accountService
                .findAccount(recipientNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Recipient account was not found."
                        )
                );

        requireUsable(recipient);

        if (sender.getAccountNumber()
                .equals(recipient.getAccountNumber())) {

            throw new IllegalArgumentException(
                    "Cannot transfer to the same account."
            );
        }

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero."
            );
        }

        // Lock accounts in a stable order to reduce deadlock risk.
        BankAccount first;
        BankAccount second;

        if (sender.getAccountNumber()
                .compareTo(recipient.getAccountNumber()) < 0) {
            first = sender;
            second = recipient;
        } else {
            first = recipient;
            second = sender;
        }

        synchronized (first) {
            synchronized (second) {
                sender.withdraw(amount);
                recipient.creditInternal(amount);
            }
        }

        transactions.add(new Transaction(
                sender.getAccountNumber(),
                Transaction.Type.TRANSFER_OUT,
                amount,
                "To " + recipient.getAccountNumber()
        ));

        transactions.add(new Transaction(
                recipient.getAccountNumber(),
                Transaction.Type.TRANSFER_IN,
                amount,
                "From " + sender.getAccountNumber()
        ));

        persist();
    }

    public synchronized void printMiniStatement(
            BankAccount account) {

        System.out.println("\n--- Recent Transactions ---");

        int shown = 0;

        for (int i = transactions.size() - 1;
             i >= 0 && shown < 5;
             i--) {

            Transaction transaction = transactions.get(i);

            if (transaction.getAccountNumber()
                    .equals(account.getAccountNumber())) {

                System.out.println(transaction);
                shown++;
            }
        }

        if (shown == 0) {
            System.out.println("No transactions found.");
        }

        System.out.println(
                "Current balance: " + account.getBalance()
        );
    }

    public synchronized List<Transaction> getTransactions() {
        return Collections.unmodifiableList(
                new ArrayList<>(transactions)
        );
    }

    public synchronized void persist() {
        fileManager.saveAccounts(
                dataFile,
                accountService.getAllAccounts()
        );

        fileManager.saveTransactions(
                transactionFile(),
                transactions
        );
    }

    private String transactionFile() {
        return dataFile.replace(
                ".ser",
                "-transactions.ser"
        );
    }

    private static void requireUsable(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException(
                    "Account is required."
            );
        }

        if (!account.isActive()) {
            throw new IllegalStateException(
                    "This account is inactive."
            );
        }

        if (account.isLocked()) {
            throw new IllegalStateException(
                    "This account is locked. Contact the administrator."
            );
        }
    }
}
