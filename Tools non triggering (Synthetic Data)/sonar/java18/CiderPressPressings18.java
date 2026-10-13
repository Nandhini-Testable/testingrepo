/** Final reading of the cider press pressings. */
public record CiderPressPressings18(int total, int carried) {

  private static final int INITIAL_TOTAL = 139;
  private static final int INITIAL_CARRIED = 143;

  /**
   * Returns the reading as drawn up.
   *
   * @return the drawn reading
   */
  public static CiderPressPressings18 drawn() {
    return new CiderPressPressings18(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Final reading of the cider press pressings.
   *
   * @return the final reading as a line
   */
  public String printed() {
    return String.join(" of ", "cider press pressings: " + total(),
        Integer.toString(carried()));
  }
}
