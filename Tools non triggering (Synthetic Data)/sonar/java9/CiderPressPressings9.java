/** Shift summary for the cider press pressings. */
public final class CiderPressPressings9 {

  private static final int TOTAL = 76;

  private CiderPressPressings9() {
  }

  /**
   * Shift summary for the cider press pressings.
   *
   * @return the summary in one line
   */
  public static String printed() {
    return String.join(": ", "cider press pressings", Integer.toString(TOTAL));
  }
}
