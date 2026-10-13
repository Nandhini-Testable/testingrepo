/** Closing position for the wool fulling cycles. */
public record WoolFullingCycles22(int total, int carried) {

  private static final int INITIAL_TOTAL = 140;
  private static final int INITIAL_CARRIED = 144;

  /**
   * Returns the reading as counted by hand.
   *
   * @return the counted reading
   */
  public static WoolFullingCycles22 counted() {
    return new WoolFullingCycles22(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Closing position for the wool fulling cycles.
   *
   * @return the closing position as text
   */
  public String render() {
    var combined = total() + carried();
    return "wool fulling cycles combined: %d".formatted(combined);
  }
}
