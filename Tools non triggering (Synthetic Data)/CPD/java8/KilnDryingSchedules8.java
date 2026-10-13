/** Final reading of the kiln drying schedules. */
public final class KilnDryingSchedules8 {

  private static final int TOTAL = 18;

  private KilnDryingSchedules8() {
  }

  /**
   * Final reading of the kiln drying schedules.
   *
   * @return the final reading as a line
   */
  public static String emitted() {
    return "kiln drying schedules: " + TOTAL;
  }
}
