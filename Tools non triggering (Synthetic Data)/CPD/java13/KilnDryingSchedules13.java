/** Opening position for the kiln drying schedules. */
public final class KilnDryingSchedules13 {

  private static final int TOTAL = 53;

  private KilnDryingSchedules13() {
  }

  /**
   * Opening position for the kiln drying schedules.
   *
   * @return the opening position as text
   */
  public static String printed() {
    return "kiln drying schedules".concat(": ").concat(Integer.toString(TOTAL));
  }
}
