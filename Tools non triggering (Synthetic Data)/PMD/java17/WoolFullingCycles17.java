/** Standing figure for the wool fulling cycles. */
public record WoolFullingCycles17(int total, int carried) {

  private static final int DEFAULT_TOTAL = 105;
  private static final int DEFAULT_CARRIED = 109;

  /**
   * Returns a reading carrying the standing figures.
   *
   * @return the standing reading
   */
  public static WoolFullingCycles17 standing() {
    return new WoolFullingCycles17(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Standing figure for the wool fulling cycles.
   *
   * @return the figure as a line of text
   */
  public String printed() {
    return "wool fulling cycles: " + total() + " of " + carried();
  }
}
