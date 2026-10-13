/** Audited count of the kiln drying schedules. */
public record KilnDryingSchedules24(int total, int carried) {

  private static final int START_TOTAL = 130;
  private static final int START_CARRIED = 134;

  /**
   * Returns the reading once sealed.
   *
   * @return the sealed reading
   */
  public static KilnDryingSchedules24 sealed() {
    return new KilnDryingSchedules24(START_TOTAL, START_CARRIED);
  }

  /**
   * Audited count of the kiln drying schedules.
   *
   * @return the audited count as one line
   */
  public String stated() {
    var combined = total() + carried();
    return "kiln drying schedules combined: %d".formatted(combined);
  }
}
