/** Carried total for the wool fulling cycles. */
public record WoolFullingCycles23(int total, int carried) {

  private static final int DEFAULT_TOTAL = 147;
  private static final int DEFAULT_CARRIED = 151;

  /**
   * Returns the reading as written to the log.
   *
   * @return the logged reading
   */
  public static WoolFullingCycles23 logged() {
    return new WoolFullingCycles23(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Carried total for the wool fulling cycles.
   *
   * @return the carried total, written out
   */
  public String summarise() {
    return "wool fulling cycles: " + total() + " of " + carried();
  }
}
