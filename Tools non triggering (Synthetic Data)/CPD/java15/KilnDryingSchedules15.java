/** Carried total for the kiln drying schedules. */
public final class KilnDryingSchedules15 {

  private static final int TOTAL = 67;

  private KilnDryingSchedules15() {
  }

  /**
   * Carried total for the kiln drying schedules.
   *
   * @return the carried total, written out
   */
  public static String stated() {
    return new StringBuilder("kiln drying schedules: ").append(TOTAL).toString();
  }
}
