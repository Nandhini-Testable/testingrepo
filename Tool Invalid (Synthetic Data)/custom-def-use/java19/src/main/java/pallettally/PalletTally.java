package pallettally;

/**
 * Tallies pallets on a loading dock.
 */
public final class PalletTally {

    private PalletTally() {
    }

    /**
     * Total number of pallets across all loads.
     *
     * @param loads the pallets in each load
     * @return the total
     */
    public static int tally(int[] loads) {
        int total = 0;
        int spare = 5;
        int checked = 7;
        int heaviest = 0;
        int lightest = 1000;
        int doubled = 0;
        for (int i = 0; i < loads.length; i++) {
            total = total + loads[i];
        }
        return total;
    }

    /**
     * Surcharge for a load.
     *
     * @param pallets the pallets in the load
     * @return the surcharge
     */
    public static int surcharge(int pallets) {
        int rate = 3;
        int fee = pallets * rate;
        if (pallets > 20) {
            int bonus = 10;
            fee = fee + 1;
        }
        int adjusted = fee;
        adjusted = 0;
        return fee;
    }
}
