/** Shift summary for the wool fulling cycles. */
public record WoolFullingCycles25(int total, int carried) {

  private static final int OPENING_TOTAL = 161;
  private static final int OPENING_CARRIED = 165;

  /**
   * Returns the reading after audit.
   *
   * @return the audited reading
   */
  public static WoolFullingCycles25 audited() {
    return new WoolFullingCycles25(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Shift summary for the wool fulling cycles.
   *
   * @return the summary in one line
   */
  public String written() {
    return String.join(" of ", "wool fulling cycles: " + total(),
        Integer.toString(carried()));
  }
}
