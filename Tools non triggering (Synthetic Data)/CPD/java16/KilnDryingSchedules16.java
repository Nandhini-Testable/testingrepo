/** Reconciled figure for the kiln drying schedules. */
public record KilnDryingSchedules16(int total, int carried) {

  private static final int BASE_TOTAL = 74;
  private static final int BASE_CARRIED = 78;

  /**
   * Returns the reading once reconciled.
   *
   * @return the settled reading
   */
  public static KilnDryingSchedules16 settled() {
    return new KilnDryingSchedules16(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Reconciled figure for the kiln drying schedules.
   *
   * @return the reconciled figure as a line
   */
  public String read() {
    return String.format("kiln drying schedules: %d of %d", total(), carried());
  }
}
