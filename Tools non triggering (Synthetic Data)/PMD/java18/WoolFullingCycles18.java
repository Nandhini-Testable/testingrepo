/** Running count of the wool fulling cycles. */
public record WoolFullingCycles18(int total, int carried) {

  private static final int BASE_TOTAL = 112;
  private static final int BASE_CARRIED = 116;

  /**
   * Returns the reading as it currently stands.
   *
   * @return the current reading
   */
  public static WoolFullingCycles18 current() {
    return new WoolFullingCycles18(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Running count of the wool fulling cycles.
   *
   * @return the count, rendered for a log
   */
  public String noted() {
    return String.format("wool fulling cycles: %d of %d", total(), carried());
  }
}
