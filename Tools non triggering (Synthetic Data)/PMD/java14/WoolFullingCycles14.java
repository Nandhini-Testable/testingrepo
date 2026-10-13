/** Audited count of the wool fulling cycles. */
public final class WoolFullingCycles14 {

  private static final int TOTAL = 84;

  private WoolFullingCycles14() {
  }

  /**
   * Audited count of the wool fulling cycles.
   *
   * @return the audited count as one line
   */
  public static String summarise() {
    return String.format("wool fulling cycles: %d", TOTAL);
  }
}
