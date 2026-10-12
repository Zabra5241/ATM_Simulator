
package atm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public final class InputValidator {

    private InputValidator() {
    }

    public static int readInt(
            Scanner scanner,
            String prompt,
            int min,
            int max) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Invalid number; ask again.
            }

            System.out.println(
                    "Enter a whole number between "
                            + min + " and " + max + "."
            );
        }
    }

    public static String readNonBlank(
            Scanner scanner,
            String prompt) {

        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }

            System.out.println("Input cannot be blank.");
        }
    }

    public static String readPin(
            Scanner scanner,
            String prompt) {

        while (true) {
            System.out.print(prompt);
            String pin = scanner.nextLine().trim();

            if (isValidPin(pin)) {
                return pin;
            }

            System.out.println(
                    "PIN must contain exactly 4 digits."
            );
        }
    }

    public static boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public static BigDecimal readMoney(
            Scanner scanner,
            String prompt,
            boolean allowZero) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                BigDecimal value = new BigDecimal(input)
                        .setScale(
                                2,
                                RoundingMode.UNNECESSARY
                        );

                if (value.signum() > 0
                        || (allowZero && value.signum() == 0)) {
                    return value;
                }
            } catch (NumberFormatException
                     | ArithmeticException ignored) {
                // Invalid amount; ask again.
            }

            System.out.println(
                    allowZero
                            ? "Enter a non-negative amount with "
                                + "at most two decimal places."
                            : "Enter an amount greater than zero "
                                + "with at most two decimal places."
            );
        }
    }
}
