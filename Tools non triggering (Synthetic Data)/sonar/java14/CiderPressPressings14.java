/** Booked figure for the cider press pressings. */
public final class CiderPressPressings14 {

  private static final int TOTAL = 111;

  private CiderPressPressings14() {
  }

  /**
   * Booked figure for the cider press pressings.
   *
   * @return the booked figure as text
   */
  public static String render() {
    return String.join(": ", "cider press pressings", Integer.toString(TOTAL));
  }
}
