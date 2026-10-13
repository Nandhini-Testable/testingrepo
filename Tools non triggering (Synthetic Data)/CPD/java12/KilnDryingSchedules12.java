/** Latest tally taken of the kiln drying schedules. */
public final class KilnDryingSchedules12 {

  private static final int TOTAL = 46;

  private KilnDryingSchedules12() {
  }

  /**
   * Latest tally taken of the kiln drying schedules.
   *
   * @return the tally in written form
   */
  public static String written() {
    return String.join(": ", "kiln drying schedules", Integer.toString(TOTAL));
  }
}
