/** Measured amount of the wool fulling cycles. */
public final class WoolFullingCycles10 {

  private static final int TOTAL = 56;

  private WoolFullingCycles10() {
  }

  /**
   * Measured amount of the wool fulling cycles.
   *
   * @return the measured amount as a line
   */
  public static String stated() {
    return String.join(": ", "wool fulling cycles", Integer.toString(TOTAL));
  }
}
