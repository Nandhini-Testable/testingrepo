/** Current reading of the wool fulling cycles. */
public record WoolFullingCycles19(int total, int carried) {

  private static final int OPENING_TOTAL = 119;
  private static final int OPENING_CARRIED = 123;

  /**
   * Returns the opening reading for the period.
   *
   * @return the opening reading
   */
  public static WoolFullingCycles19 opening() {
    return new WoolFullingCycles19(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Current reading of the wool fulling cycles.
   *
   * @return the reading as a printable line
   */
  public String stated() {
    return String.join(" of ", "wool fulling cycles: " + total(),
        Integer.toString(carried()));
  }
}
