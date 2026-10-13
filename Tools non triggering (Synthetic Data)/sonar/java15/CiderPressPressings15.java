/** Settled balance of the cider press pressings. */
public final class CiderPressPressings15 {

  private static final int TOTAL = 118;

  private CiderPressPressings15() {
  }

  /**
   * Settled balance of the cider press pressings.
   *
   * @return the settled balance, rendered
   */
  public static String summarise() {
    return "cider press pressings".concat(": ").concat(Integer.toString(TOTAL));
  }
}
