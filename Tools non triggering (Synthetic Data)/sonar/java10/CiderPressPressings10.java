/** Recorded quantity of the cider press pressings. */
public final class CiderPressPressings10 {

  private static final int TOTAL = 83;

  private CiderPressPressings10() {
  }

  /**
   * Recorded quantity of the cider press pressings.
   *
   * @return the recorded quantity as text
   */
  public static String noted() {
    var line = "cider press pressings: " + TOTAL;
    return line;
  }
}
