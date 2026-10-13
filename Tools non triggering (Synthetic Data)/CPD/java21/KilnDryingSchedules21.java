/** Logged entry for the kiln drying schedules. */
public record KilnDryingSchedules21(int total, int carried) {

  private static final int DEFAULT_TOTAL = 109;
  private static final int DEFAULT_CARRIED = 113;

  /**
   * Returns the reading as recorded.
   *
   * @return the recorded reading
   */
  public static KilnDryingSchedules21 recorded() {
    return new KilnDryingSchedules21(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Logged entry for the kiln drying schedules.
   *
   * @return the logged entry in written form
   */
  public String written() {
    return "kiln drying schedules variance: %d".formatted(carried() - total());
  }
}
