/** Current reading of the cider press pressings. */
public record CiderPressPressings21(int total, int carried) {

  private static final int OPENING_TOTAL = 160;
  private static final int OPENING_CARRIED = 164;

  /**
   * Returns the opening reading for the period.
   *
   * @return the opening reading
   */
  public static CiderPressPressings21 opening() {
    return new CiderPressPressings21(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Current reading of the cider press pressings.
   *
   * @return the reading as a printable line
   */
  public String read() {
    return "cider press pressings: " + total() + " of " + carried();
  }
}
