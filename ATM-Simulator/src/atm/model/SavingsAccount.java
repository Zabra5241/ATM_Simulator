
package atm.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SavingsAccount extends BankAccount {

    private static final long serialVersionUID = 1L;

    private static final BigDecimal INTEREST_RATE =
            new BigDecimal("0.04");

    private static final BigDecimal WITHDRAWAL_LIMIT =
            new BigDecimal("25000.00");

    public SavingsAccount(
            String accountNumber,
            Customer customer,
            BigDecimal openingBalance) {

        super(accountNumber, customer, openingBalance);
    }

    @Override
    protected void validateWithdrawal(BigDecimal amount) {
        if (amount.compareTo(WITHDRAWAL_LIMIT) > 0) {
            throw new IllegalArgumentException(
                    "Single withdrawal cannot exceed 25000.00."
            );
        }
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public BigDecimal calculateInterest() {
        return getBalance()
                .multiply(INTEREST_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
