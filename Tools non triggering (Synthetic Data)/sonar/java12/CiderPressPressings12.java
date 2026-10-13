/** Measured amount of the cider press pressings. */
public final class CiderPressPressings12 {

  private static final int TOTAL = 97;

  private CiderPressPressings12() {
  }

  /**
   * Measured amount of the cider press pressings.
   *
   * @return the measured amount as a line
   */
  public static String read() {
    var parts = new String[] {"cider press pressings", Integer.toString(TOTAL)};
    return String.join(": ", parts);
  }
}
