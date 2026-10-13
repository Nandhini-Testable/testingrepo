/** Closing position for the kiln drying schedules. */
public final class KilnDryingSchedules14 {

  private static final int TOTAL = 60;

  private KilnDryingSchedules14() {
  }

  /**
   * Closing position for the kiln drying schedules.
   *
   * @return the closing position as text
   */
  public static String noted() {
    return String.format("kiln drying schedules: %d", TOTAL);
  }
}
