package tollbooth;

import java.util.HashMap;
import java.util.Map;

/**
 * Records the axle count of each vehicle that passes a toll booth and prices the crossing.
 */
public final class TollBooth {

    private static final int RATE_PER_AXLE = 150;

    private final Map<String, Integer> axleCounts = new HashMap<String, Integer>();
    private String operator;
    @Nullable
    private String lastPlate;

    /**
     * Records a vehicle.
     *
     * @param plate the licence plate
     * @param axles the number of axles
     */
    public void record(String plate, int axles) {
        axleCounts.put(plate, Integer.valueOf(axles));
        lastPlate = plate;
    }

    /**
     * Axle count of a plate.
     *
     * @param plate the licence plate
     * @return the axle count, or null when the plate was never recorded
     */
    @Nullable
    public Integer axlesFor(String plate) {
        return axleCounts.get(plate);
    }

    /**
     * Toll owed by a plate.
     *
     * @param plate the licence plate
     * @return the toll
     */
    public int toll(String plate) {
        return axlesFor(plate).intValue() * RATE_PER_AXLE;
    }

    /**
     * Length of the most recently recorded plate.
     *
     * @return the length
     */
    public int lastPlateLength() {
        return lastPlate.length();
    }

    /**
     * Receipt text for a plate.
     *
     * @param plate the licence plate
     * @return the receipt
     */
    public String receipt(String plate) {
        if (!axleCounts.containsKey(plate)) {
            return null;
        }
        return "PLATE " + plate + " AXLES " + axleCounts.get(plate);
    }

    /**
     * The operator's badge text.
     *
     * @return the badge in upper case
     */
    public String operatorBadge() {
        return operator.toUpperCase();
    }

    /**
     * A stamp for the day's audit sheet.
     *
     * @return the stamp
     */
    public String auditStamp() {
        return stamp(null);
    }

    private String stamp(String text) {
        return "AUDIT:" + text.trim();
    }
}
