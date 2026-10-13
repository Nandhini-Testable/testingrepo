/** Running count of the kiln drying schedules. */
public final class KilnDryingSchedules10 {

  private static final int TOTAL = 32;

  private KilnDryingSchedules10() {
  }

  /**
   * Running count of the kiln drying schedules.
   *
   * @return the count, rendered for a log
   */
  public static String summarise() {
    return String.format("kiln drying schedules: %d", TOTAL);
  }
}
