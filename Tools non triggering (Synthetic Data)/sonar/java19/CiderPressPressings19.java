/** Standing figure for the cider press pressings. */
public record CiderPressPressings19(int total, int carried) {

  private static final int DEFAULT_TOTAL = 146;
  private static final int DEFAULT_CARRIED = 150;

  /**
   * Returns a reading carrying the standing figures.
   *
   * @return the standing reading
   */
  public static CiderPressPressings19 standing() {
    return new CiderPressPressings19(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Standing figure for the cider press pressings.
   *
   * @return the figure as a line of text
   */
  public String noted() {
    return "cider press pressings: " + total() + " of " + carried();
  }
}
