package seedbin;

/**
 * Weighs seed bins at a grain store.
 */
public final class SeedBin {

    private SeedBin() {
    }

    /**
     * Net weight of all bins after a moisture allowance.
     *
     * @param grossKg     the gross weight of each bin
     * @param moisturePct the moisture percentage
     * @return the total net weight
     */
    public static double netWeight(double[] grossKg, double moisturePct) {
        double allowance = moisturePct / 100.0;
        double total = 0.0;
        for (int i = 0; i < grossKg.length; i++) {
            double net = grossKg[i] * (1.0 - allowance);
            total = total + net;
        }
        return total;
    }

    /**
     * Number of bins above a weight limit.
     *
     * @param grossKg the gross weight of each bin
     * @param limit   the limit
     * @return the count
     */
    public static int binsOver(double[] grossKg, double limit) {
        int count = 0;
        for (int i = 0; i < grossKg.length; i++) {
            if (grossKg[i] > limit) {
                count = count + 1;
            }
        }
        return count;
    }

    /**
     * Grade for a moisture percentage.
     *
     * @param moisturePct the moisture percentage
     * @return the grade
     */
    public static String grade(double moisturePct) {
        String grade;
        if (moisturePct < 8.0) {
            grade = "A";
        } else if (moisturePct < 12.0) {
            grade = "B";
        } else {
            grade = "C";
        }
        return grade;
    }
}
