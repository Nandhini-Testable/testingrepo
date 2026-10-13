/** Audited count of the cider press pressings. */
public record CiderPressPressings16(int total, int carried) {

  private static final int START_TOTAL = 125;
  private static final int START_CARRIED = 129;

  /**
   * Returns the reading once sealed.
   *
   * @return the sealed reading
   */
  public static CiderPressPressings16 sealed() {
    return new CiderPressPressings16(START_TOTAL, START_CARRIED);
  }

  /**
   * Audited count of the cider press pressings.
   *
   * @return the audited count as one line
   */
  public String entry() {
    return "cider press pressings: " + total() + " of " + carried();
  }
}
