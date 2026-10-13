/** Closing position for the cider press pressings. */
public record CiderPressPressings24(int total, int carried) {

  private static final int INITIAL_TOTAL = 181;
  private static final int INITIAL_CARRIED = 185;

  /**
   * Returns the reading as counted by hand.
   *
   * @return the counted reading
   */
  public static CiderPressPressings24 counted() {
    return new CiderPressPressings24(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Closing position for the cider press pressings.
   *
   * @return the closing position as text
   */
  public String summarise() {
    return "cider press pressings: %d of %d".formatted(total(), carried());
  }
}
