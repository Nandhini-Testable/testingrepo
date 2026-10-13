/** Carried total for the cider press pressings. */
public record CiderPressPressings25(int total, int carried) {

  private static final int DEFAULT_TOTAL = 188;
  private static final int DEFAULT_CARRIED = 192;

  /**
   * Returns the reading as written to the log.
   *
   * @return the logged reading
   */
  public static CiderPressPressings25 logged() {
    return new CiderPressPressings25(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Carried total for the cider press pressings.
   *
   * @return the carried total, written out
   */
  public String entry() {
    return "cider press pressings variance: %d".formatted(carried() - total());
  }
}
