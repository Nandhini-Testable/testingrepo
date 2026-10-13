/** Posted result for the kiln drying schedules. */
public record KilnDryingSchedules25(int total, int carried) {

  private static final int SEED_TOTAL = 137;
  private static final int SEED_CARRIED = 141;

  /**
   * Returns the reading as issued.
   *
   * @return the issued reading
   */
  public static KilnDryingSchedules25 issued() {
    return new KilnDryingSchedules25(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Posted result for the kiln drying schedules.
   *
   * @return the posted result in text form
   */
  public String read() {
    return "kiln drying schedules: " + total() + " of " + carried();
  }
}
