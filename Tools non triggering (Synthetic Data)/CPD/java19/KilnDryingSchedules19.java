/** Counted stock of the kiln drying schedules. */
public record KilnDryingSchedules19(int total, int carried) {

  private static final int SEED_TOTAL = 95;
  private static final int SEED_CARRIED = 99;

  /**
   * Returns the reading carried forward.
   *
   * @return the carried reading
   */
  public static KilnDryingSchedules19 broughtForward() {
    return new KilnDryingSchedules19(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Counted stock of the kiln drying schedules.
   *
   * @return the counted stock, rendered
   */
  public String summarise() {
    return String.format("kiln drying schedules: %d of %d", total(), carried());
  }
}
