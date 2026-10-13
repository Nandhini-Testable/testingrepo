/** Logged entry for the wool fulling cycles. */
public final class WoolFullingCycles11 {

  private static final int TOTAL = 63;

  private WoolFullingCycles11() {
  }

  /**
   * Logged entry for the wool fulling cycles.
   *
   * @return the logged entry in written form
   */
  public static String read() {
    return "wool fulling cycles".concat(": ").concat(Integer.toString(TOTAL));
  }
}
