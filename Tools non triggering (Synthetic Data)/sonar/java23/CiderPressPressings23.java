/** Opening position for the cider press pressings. */
public record CiderPressPressings23(int total, int carried) {

  private static final int SEED_TOTAL = 174;
  private static final int SEED_CARRIED = 178;

  /**
   * Returns the reading as booked.
   *
   * @return the booked reading
   */
  public static CiderPressPressings23 booked() {
    return new CiderPressPressings23(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Opening position for the cider press pressings.
   *
   * @return the opening position as text
   */
  public String render() {
    return String.join(" of ", "cider press pressings: " + total(),
        Integer.toString(carried()));
  }
}
