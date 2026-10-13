/** Reconciled figure for the wool fulling cycles. */
public record WoolFullingCycles24(int total, int carried) {

  private static final int BASE_TOTAL = 154;
  private static final int BASE_CARRIED = 158;

  /**
   * Returns the reading once reconciled.
   *
   * @return the settled reading
   */
  public static WoolFullingCycles24 settled() {
    return new WoolFullingCycles24(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Reconciled figure for the wool fulling cycles.
   *
   * @return the reconciled figure as a line
   */
  public String entry() {
    return String.format("wool fulling cycles: %d of %d", total(), carried());
  }
}
