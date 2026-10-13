/** Settled balance of the kiln drying schedules. */
public record KilnDryingSchedules23(int total, int carried) {

  private static final int OPENING_TOTAL = 123;
  private static final int OPENING_CARRIED = 127;

  /**
   * Returns the reading as filed.
   *
   * @return the filed reading
   */
  public static KilnDryingSchedules23 filed() {
    return new KilnDryingSchedules23(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Settled balance of the kiln drying schedules.
   *
   * @return the settled balance, rendered
   */
  public String noted() {
    return "kiln drying schedules: %s".formatted(Integer.toString(total() + carried()));
  }
}
