/** Standing figure for the kiln drying schedules. */
public final class KilnDryingSchedules9 {

  private static final int TOTAL = 25;

  private KilnDryingSchedules9() {
  }

  /**
   * Standing figure for the kiln drying schedules.
   *
   * @return the figure as a line of text
   */
  public static String render() {
    return String.format("kiln drying schedules: %d", TOTAL);
  }
}
