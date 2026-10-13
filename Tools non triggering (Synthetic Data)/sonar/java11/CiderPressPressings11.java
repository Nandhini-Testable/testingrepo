/** Counted stock of the cider press pressings. */
public final class CiderPressPressings11 {

  private static final int TOTAL = 90;

  private CiderPressPressings11() {
  }

  /**
   * Counted stock of the cider press pressings.
   *
   * @return the counted stock, rendered
   */
  public static String stated() {
    var figure = Integer.valueOf(TOTAL);
    return "cider press pressings: " + figure;
  }
}
