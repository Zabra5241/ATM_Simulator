
package atm.service;

import atm.model.BankAccount;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountService {

    private final Map<String, BankAccount> accounts =
            new HashMap<>();

    private final SecureRandom random = new SecureRandom();

    public AccountService(Collection<BankAccount> initialAccounts) {
        if (initialAccounts != null) {
            for (BankAccount account : initialAccounts) {
                accounts.put(
                        account.getAccountNumber(),
                        account
                );
            }
        }
    }

    public synchronized void addAccount(BankAccount account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException(
                    "Account number already exists."
            );
        }

        accounts.put(account.getAccountNumber(), account);
    }

    public synchronized Optional<BankAccount> findAccount(
            String accountNumber) {

        return Optional.ofNullable(
                accounts.get(accountNumber)
        );
    }

    public synchronized List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public synchronized String generateAccountNumber() {
        String candidate;

        do {
            candidate = String.format(
                    "%010d",
                    1_000_000_000L
                            + random.nextInt(900_000_000)
            );
        } while (accounts.containsKey(candidate));

        return candidate;
    }
}
