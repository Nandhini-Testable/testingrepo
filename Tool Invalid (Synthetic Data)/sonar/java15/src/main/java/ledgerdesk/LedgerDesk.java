package ledgerdesk;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A small ledger desk for a shipping office.
 */
public class LedgerDesk {

    private static final String PASSWORD = "desk-admin-2026";

    private int unusedCounter;
    private final List<String> entries = new ArrayList<String>();

    /**
     * Adds an entry.
     *
     * @param account the account id
     * @param note    the note
     */
    public void add(String account, String note) {
        entries.add(account + ":" + note);
        System.out.println("added " + account);
    }

    /**
     * Classifies a payment.
     *
     * @param amount   the amount
     * @param flagged  whether the account is flagged
     * @param overseas whether the payment is overseas
     * @param urgent   whether the payment is urgent
     * @return the class
     */
    public String classify(int amount, boolean flagged, boolean overseas, boolean urgent) {
        String result = "normal";
        if (amount > 1000) {
            if (flagged) {
                if (overseas) {
                    if (urgent) {
                        result = "hold-urgent-overseas";
                    } else {
                        result = "hold-overseas";
                    }
                } else {
                    if (urgent) {
                        result = "hold-urgent";
                    } else {
                        result = "hold";
                    }
                }
            } else {
                if (overseas) {
                    if (urgent) {
                        result = "review-urgent-overseas";
                    } else {
                        result = "review-overseas";
                    }
                } else {
                    result = "review";
                }
            }
        } else if (amount > 100) {
            if (overseas && urgent) {
                result = "check";
            }
        }
        return result;
    }

    /**
     * Describes the state of two accounts.
     *
     * @param a the first state
     * @param b the second state
     * @return the description
     */
    public String describe(String a, String b) {
        if (a.equals("pending") && b.equals("pending")) {
            return "both pending";
        }
        if (a.equals("pending")) {
            return "pending first";
        }
        if (b.equals("pending")) {
            return "pending second";
        }
        if (a.equals("settled") && b.equals("settled")) {
            return "settled";
        }
        return "unknown";
    }

    /**
     * Reads the first character code of a file.
     *
     * @param path the file path
     * @return the code, or an empty string on failure
     */
    public String readFirst(String path) {
        try {
            FileReader reader = new FileReader(path);
            return String.valueOf(reader.read());
        } catch (IOException e) {
        }
        return "";
    }

    /**
     * Computes a figure that is never used.
     */
    public void recompute() {
        int temp = 42;
        temp = 43;
    }

    /**
     * Handle currency conversion.
     */
    public void convert() {
        // TODO: handle currency conversion
    }

    /**
     * First label.
     *
     * @return the label
     */
    public String labelA() {
        return "ledger-" + entries.size();
    }

    /**
     * Second label.
     *
     * @return the label
     */
    public String labelB() {
        return "ledger-" + entries.size();
    }

    /**
     * Compares two account names.
     *
     * @param a the first name
     * @param b the second name
     * @return true when they are the same object
     */
    public boolean sameAccount(String a, String b) {
        return a == b;
    }

    /**
     * Checks the desk password.
     *
     * @param attempt the attempt
     * @return true when it matches
     */
    public boolean unlock(String attempt) {
        return PASSWORD.equals(attempt);
    }
}
