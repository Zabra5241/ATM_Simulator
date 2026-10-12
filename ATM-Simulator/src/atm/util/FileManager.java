
package atm.util;

import atm.model.BankAccount;
import atm.model.Transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileManager {

    public synchronized void saveAccounts(
            String filename,
            Collection<BankAccount> accounts) {

        writeObjects(filename, new ArrayList<>(accounts));
    }

    public synchronized List<BankAccount> loadAccounts(
            String filename) {

        Object value = readObject(filename);

        if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            List<BankAccount> result = new ArrayList<>();

            for (Object item : list) {
                if (!(item instanceof BankAccount)) {
                    return new ArrayList<>();
                }

                result.add((BankAccount) item);
            }

            return result;
        }

        return new ArrayList<>();
    }

    public synchronized void saveTransactions(
            String filename,
            Collection<Transaction> transactions) {

        writeObjects(filename, new ArrayList<>(transactions));
    }

    public synchronized List<Transaction> loadTransactions(
            String filename) {

        Object value = readObject(filename);

        if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            List<Transaction> result = new ArrayList<>();

            for (Object item : list) {
                if (!(item instanceof Transaction)) {
                    return new ArrayList<>();
                }

                result.add((Transaction) item);
            }

            return result;
        }

        return new ArrayList<>();
    }

    private void writeObjects(String filename, Object value) {
        Path path = Path.of(filename);

        try {
            Path parent = path.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (ObjectOutputStream output =
                         new ObjectOutputStream(
                                 Files.newOutputStream(path))) {

                output.writeObject(value);
            }

        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Could not save data to " + filename,
                    ex
            );
        }
    }

    private Object readObject(String filename) {
        Path path = Path.of(filename);

        if (!Files.exists(path)) {
            return null;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             Files.newInputStream(path))) {

            return input.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(
                    "Warning: could not read "
                            + filename + ": " + ex.getMessage()
            );

            return null;
        }
    }
}
