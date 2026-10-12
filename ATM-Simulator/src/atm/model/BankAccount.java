
package atm.model;

import atm.exceptions.InsufficientBalanceException;
import atm.interfaces.ATMOperations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class BankAccount
        implements ATMOperations, Serializable {

    private static final long serialVersionUID = 1L;

    private final String accountNumber;
    private final Customer customer;

    private BigDecimal balance;
    private boolean active = true;
    private int failedPinAttempts;
    private boolean locked;

    protected BankAccount(
            String accountNumber,
            Customer customer,
            BigDecimal openingBalance) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Account number is required."
            );
        }

        this.accountNumber = accountNumber;
        this.customer = Objects.requireNonNull(customer);

        this.balance = normalizeMoney(openingBalance);

        if (balance.signum() < 0) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative."
            );
        }
    }

    private static BigDecimal normalizeMoney(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount is required.");

        return amount.setScale(
                2, RoundingMode.UNNECESSARY
        );
    }

    @Override
    public final synchronized void deposit(BigDecimal amount) {
        BigDecimal value = normalizeMoney(amount);

        if (value.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        balance = balance.add(value);
    }

    @Override
    public final synchronized void withdraw(BigDecimal amount) {
        BigDecimal value = normalizeMoney(amount);

        if (value.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        if (value.compareTo(balance) > 0) {
            throw new InsufficientBalanceException(
                    "Insufficient account balance."
            );
        }

        validateWithdrawal(value);
        balance = balance.subtract(value);
    }

    // Subclasses provide their own withdrawal rules.
    protected abstract void validateWithdrawal(
            BigDecimal amount
    );

    public abstract String getAccountType();

    public abstract BigDecimal calculateInterest();

    @Override
    public final synchronized BigDecimal getBalance() {
        return balance;
    }

    public final String getAccountNumber() {
        return accountNumber;
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final synchronized boolean isActive() {
        return active;
    }

    public final synchronized boolean isLocked() {
        return locked;
    }

    public final synchronized int getFailedPinAttempts() {
        return failedPinAttempts;
    }

    public final synchronized void recordFailedPinAttempt() {
        failedPinAttempts++;

        if (failedPinAttempts >= 3) {
            locked = true;
        }
    }

    public final synchronized void resetFailedPinAttempts() {
        failedPinAttempts = 0;
    }

    public final synchronized void setActive(boolean active) {
        this.active = active;
    }

    public final synchronized void setLocked(boolean locked) {
        this.locked = locked;

        if (!locked) {
            failedPinAttempts = 0;
        }
    }

    // Used internally for the receiving side of a transfer.
    public final synchronized void creditInternal(
            BigDecimal amount) {

        BigDecimal value = normalizeMoney(amount);

        if (value.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }

        balance = balance.add(value);
    }

    @Override
    public String toString() {
        return getAccountType()
                + " Account{accountNumber='"
                + accountNumber
                + "', customer='"
                + customer.getName()
                + "', balance="
                + getBalance()
                + ", active="
                + active
                + ", locked="
                + locked
                + "}";
    }
}
