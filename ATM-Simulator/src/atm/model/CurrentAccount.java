
package atm.model;

import java.math.BigDecimal;

public final class CurrentAccount extends BankAccount {

    private static final long serialVersionUID = 1L;

    private static final BigDecimal WITHDRAWAL_LIMIT =
            new BigDecimal("50000.00");

    public CurrentAccount(
            String accountNumber,
            Customer customer,
            BigDecimal openingBalance) {

        super(accountNumber, customer, openingBalance);
    }

    @Override
    protected void validateWithdrawal(BigDecimal amount) {
        if (amount.compareTo(WITHDRAWAL_LIMIT) > 0) {
            throw new IllegalArgumentException(
                    "Single withdrawal cannot exceed 50000.00."
            );
        }
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public BigDecimal calculateInterest() {
        return BigDecimal.ZERO.setScale(2);
    }
}
