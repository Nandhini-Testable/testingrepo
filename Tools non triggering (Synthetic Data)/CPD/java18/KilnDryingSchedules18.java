/** Recorded quantity of the kiln drying schedules. */
public record KilnDryingSchedules18(int total, int carried) {

  private static final int START_TOTAL = 88;
  private static final int START_CARRIED = 92;

  /**
   * Returns the reading as posted.
   *
   * @return the posted reading
   */
  public static KilnDryingSchedules18 posted() {
    return new KilnDryingSchedules18(START_TOTAL, START_CARRIED);
  }

  /**
   * Recorded quantity of the kiln drying schedules.
   *
   * @return the recorded quantity as text
   */
  public String render() {
    return "kiln drying schedules: " + total() + " of " + carried();
  }
}
