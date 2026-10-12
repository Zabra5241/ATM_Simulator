
package atm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );

    public enum Type {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER_OUT,
        TRANSFER_IN
    }

    private final String referenceId;
    private final String accountNumber;
    private final Type type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String note;

    public Transaction(
            String accountNumber,
            Type type,
            BigDecimal amount,
            String note) {

        this.referenceId = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.note = note == null ? "" : note;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp.format(FORMATTER)
                + " | " + referenceId
                + " | " + type
                + " | " + amount.toPlainString()
                + (note.isBlank() ? "" : " | " + note);
    }
}
