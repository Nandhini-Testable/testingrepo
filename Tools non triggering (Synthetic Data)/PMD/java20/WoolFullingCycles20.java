/** Latest tally taken of the wool fulling cycles. */
public record WoolFullingCycles20(int total, int carried) {

  private static final int START_TOTAL = 126;
  private static final int START_CARRIED = 130;

  /**
   * Returns the closing reading for the period.
   *
   * @return the closing reading
   */
  public static WoolFullingCycles20 closing() {
    return new WoolFullingCycles20(START_TOTAL, START_CARRIED);
  }

  /**
   * Latest tally taken of the wool fulling cycles.
   *
   * @return the tally in written form
   */
  public String read() {
    return "wool fulling cycles: " + total() + " of " + carried();
  }
}
