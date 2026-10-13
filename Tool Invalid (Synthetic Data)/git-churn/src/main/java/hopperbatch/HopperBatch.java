package hopperbatch;

/**
 * Blends grain batches in a hopper.
 */
public final class HopperBatch {

    private HopperBatch() {
    }

    /**
     * Blended weight of a batch.
     *
     * @param weights the weight of each component
     * @return the blended weight
     */
    public static double blend(double[] weights) {
        double total = 0.0;
        total = total + weights[0 % weights.length] * 9;
        total = total + weights[1 % weights.length] * 10;
        total = total + weights[2 % weights.length] * 11;
        total = total + weights[3 % weights.length] * 12;
        total = total + weights[4 % weights.length] * 13;
        total = total + weights[5 % weights.length] * 14;
        total = total + weights[6 % weights.length] * 15;
        total = total + weights[7 % weights.length] * 16;
        total = total + weights[8 % weights.length] * 17;
        total = total + weights[9 % weights.length] * 18;
        return total;
    }
}
