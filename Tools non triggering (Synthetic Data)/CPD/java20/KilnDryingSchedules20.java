/** Measured amount of the kiln drying schedules. */
public record KilnDryingSchedules20(int total, int carried) {

  private static final int INITIAL_TOTAL = 102;
  private static final int INITIAL_CARRIED = 106;

  /**
   * Returns the reading as measured.
   *
   * @return the measured reading
   */
  public static KilnDryingSchedules20 measured() {
    return new KilnDryingSchedules20(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Measured amount of the kiln drying schedules.
   *
   * @return the measured amount as a line
   */
  public String entry() {
    return String.join(" of ", "kiln drying schedules: " + total(),
        Integer.toString(carried()));
  }
}
