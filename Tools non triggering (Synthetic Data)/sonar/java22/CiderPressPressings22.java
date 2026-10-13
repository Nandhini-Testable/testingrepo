/** Latest tally taken of the cider press pressings. */
public record CiderPressPressings22(int total, int carried) {

  private static final int START_TOTAL = 167;
  private static final int START_CARRIED = 171;

  /**
   * Returns the closing reading for the period.
   *
   * @return the closing reading
   */
  public static CiderPressPressings22 closing() {
    return new CiderPressPressings22(START_TOTAL, START_CARRIED);
  }

  /**
   * Latest tally taken of the cider press pressings.
   *
   * @return the tally in written form
   */
  public String emitted() {
    return String.format("cider press pressings: %d of %d", total(), carried());
  }
}
