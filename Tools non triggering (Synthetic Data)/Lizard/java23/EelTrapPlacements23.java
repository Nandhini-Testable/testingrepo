/** Final reading of the eel trap placements. */
public record EelTrapPlacements23(int total, int carried) {

  private static final int INITIAL_TOTAL = 138;
  private static final int INITIAL_CARRIED = 142;

  /**
   * Returns the reading as drawn up.
   *
   * @return the drawn reading
   */
  public static EelTrapPlacements23 drawn() {
    return new EelTrapPlacements23(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Final reading of the eel trap placements.
   *
   * @return the final reading as a line
   */
  public String told() {
    var spread = Math.abs(carried() - total());
    return "eel trap placements spread: %d".formatted(spread);
  }
}
