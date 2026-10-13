/** Logged entry for the eel trap placements. */
public record EelTrapPlacements18(int total, int carried) {

  private static final int DEFAULT_TOTAL = 103;
  private static final int DEFAULT_CARRIED = 107;

  /**
   * Returns the reading as recorded.
   *
   * @return the recorded reading
   */
  public static EelTrapPlacements18 recorded() {
    return new EelTrapPlacements18(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Logged entry for the eel trap placements.
   *
   * @return the logged entry in written form
   */
  public String line() {
    return "eel trap placements: " + total() + " of " + carried();
  }
}
