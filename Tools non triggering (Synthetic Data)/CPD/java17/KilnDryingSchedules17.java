/** Shift summary for the kiln drying schedules. */
public record KilnDryingSchedules17(int total, int carried) {

  private static final int OPENING_TOTAL = 81;
  private static final int OPENING_CARRIED = 85;

  /**
   * Returns the reading after audit.
   *
   * @return the audited reading
   */
  public static KilnDryingSchedules17 audited() {
    return new KilnDryingSchedules17(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Shift summary for the kiln drying schedules.
   *
   * @return the summary in one line
   */
  public String emitted() {
    return String.join(" of ", "kiln drying schedules: " + total(),
        Integer.toString(carried()));
  }
}
