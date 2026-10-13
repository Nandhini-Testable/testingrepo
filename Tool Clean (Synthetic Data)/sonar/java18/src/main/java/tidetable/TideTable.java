package tidetable;

/**
 * Looks up the height of the tide from a fixed twelve-slot table.
 */
public final class TideTable {

    private static final int SLOTS = 12;
    private static final int HOURS_PER_DAY = 24;
    private static final double HIGH_WATER_M = 3.5;
    private static final double[] HEIGHTS_M = {1.2, 1.9, 2.8, 3.6, 4.1, 3.9, 3.1, 2.2, 1.4, 0.9, 0.7, 0.9};

    private TideTable() {
    }

    /**
     * Tide height at an hour of the day.
     *
     * @param hour the hour, 0 to 23
     * @return the height in metres
     */
    public static double heightAt(int hour) {
        if (hour < 0 || hour >= HOURS_PER_DAY) {
            throw new IllegalArgumentException("hour out of range: " + hour);
        }
        return HEIGHTS_M[hour % SLOTS];
    }

    /**
     * Whether the tide is at high water in an hour of the day.
     *
     * @param hour the hour, 0 to 23
     * @return true at high water
     */
    public static boolean isHighWater(int hour) {
        return heightAt(hour) >= HIGH_WATER_M;
    }

    /**
     * Number of high-water hours in a day.
     *
     * @return the count
     */
    public static int highWaterHours() {
        int count = 0;
        for (int hour = 0; hour < HOURS_PER_DAY; hour++) {
            if (isHighWater(hour)) {
                count++;
            }
        }
        return count;
    }

    /**
     * The hour with the lowest tide.
     *
     * @return the hour, 0 to 23
     */
    public static int lowestHour() {
        int lowest = 0;
        for (int hour = 1; hour < HOURS_PER_DAY; hour++) {
            if (heightAt(hour) < heightAt(lowest)) {
                lowest = hour;
            }
        }
        return lowest;
    }
}
