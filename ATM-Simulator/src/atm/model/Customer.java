
package atm.model;

import java.io.Serializable;
import java.util.Objects;

public final class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final String phone;
    private String pinHash;

    public Customer(String name, String phone, String pinHash) {
        this.name = requireText(name, "Name");
        this.phone = requireText(phone, "Phone");
        this.pinHash = requireText(pinHash, "PIN hash");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " cannot be blank."
            );
        }
        return value.trim();
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean matchesPinHash(String candidateHash) {
        return pinHash.equals(
                Objects.requireNonNull(candidateHash)
        );
    }

    public void updatePinHash(String newHash) {
        this.pinHash = requireText(newHash, "PIN hash");
    }
}
