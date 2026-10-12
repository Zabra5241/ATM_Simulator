
package atm.service;

import atm.model.BankAccount;
import atm.util.InputValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {

    private final AccountService accountService;

    public AuthService(AccountService accountService) {
        this.accountService = accountService;
    }

    public BankAccount authenticate(
            String accountNumber,
            String pin) {

        BankAccount account = accountService
                .findAccount(accountNumber)
                .orElse(null);

        if (account == null
                || !account.isActive()
                || account.isLocked()) {
            return null;
        }

        if (!InputValidator.isValidPin(pin)) {
            account.recordFailedPinAttempt();
            return null;
        }

        boolean valid = account.getCustomer()
                .matchesPinHash(hashPin(pin));

        if (valid) {
            account.resetFailedPinAttempts();
            return account;
        }

        account.recordFailedPinAttempt();
        return null;
    }

    public boolean changePin(
            BankAccount account,
            String oldPin,
            String newPin) {

        if (!InputValidator.isValidPin(oldPin)
                || !InputValidator.isValidPin(newPin)) {
            return false;
        }

        if (!account.getCustomer()
                .matchesPinHash(hashPin(oldPin))) {
            return false;
        }

        account.getCustomer().updatePinHash(hashPin(newPin));
        return true;
    }

    public static String hashPin(String pin) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] bytes = digest.digest(
                    pin.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder result = new StringBuilder();

            for (byte b : bytes) {
                result.append(String.format("%02x", b));
            }

            return result.toString();

        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(
                    "SHA-256 is unavailable.",
                    ex
            );
        }
    }
}
