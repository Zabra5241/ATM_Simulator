
package atm.interfaces;

import java.math.BigDecimal;

public interface ATMOperations {
    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);

    BigDecimal getBalance();
}
