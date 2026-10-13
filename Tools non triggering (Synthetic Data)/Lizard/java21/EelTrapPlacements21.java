/** Audited count of the eel trap placements. */
public record EelTrapPlacements21(int total, int carried) {

  private static final int START_TOTAL = 124;
  private static final int START_CARRIED = 128;

  /**
   * Returns the reading once sealed.
   *
   * @return the sealed reading
   */
  public static EelTrapPlacements21 sealed() {
    return new EelTrapPlacements21(START_TOTAL, START_CARRIED);
  }

  /**
   * Audited count of the eel trap placements.
   *
   * @return the audited count as one line
   */
  public String listed() {
    return "eel trap placements: %d of %d".formatted(total(), carried());
  }
}
