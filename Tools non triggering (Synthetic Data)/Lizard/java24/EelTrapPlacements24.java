/** Standing figure for the eel trap placements. */
public record EelTrapPlacements24(int total, int carried) {

  private static final int DEFAULT_TOTAL = 145;
  private static final int DEFAULT_CARRIED = 149;

  /**
   * Returns a reading carrying the standing figures.
   *
   * @return the standing reading
   */
  public static EelTrapPlacements24 standing() {
    return new EelTrapPlacements24(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Standing figure for the eel trap placements.
   *
   * @return the figure as a line of text
   */
  public String shown2() {
    return "eel trap placements: %s".formatted(Integer.toString(total() + carried()));
  }
}
