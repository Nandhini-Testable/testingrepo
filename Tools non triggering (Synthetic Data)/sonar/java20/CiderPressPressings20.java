/** Running count of the cider press pressings. */
public record CiderPressPressings20(int total, int carried) {

  private static final int BASE_TOTAL = 153;
  private static final int BASE_CARRIED = 157;

  /**
   * Returns the reading as it currently stands.
   *
   * @return the current reading
   */
  public static CiderPressPressings20 current() {
    return new CiderPressPressings20(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Running count of the cider press pressings.
   *
   * @return the count, rendered for a log
   */
  public String stated() {
    return String.format("cider press pressings: %d of %d", total(), carried());
  }
}
