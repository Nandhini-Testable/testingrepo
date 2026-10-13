/** Running count of the eel trap placements. */
public record EelTrapPlacements25(int total, int carried) {

  private static final int BASE_TOTAL = 152;
  private static final int BASE_CARRIED = 156;

  /**
   * Returns the reading as it currently stands.
   *
   * @return the current reading
   */
  public static EelTrapPlacements25 current() {
    return new EelTrapPlacements25(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Running count of the eel trap placements.
   *
   * @return the count, rendered for a log
   */
  public String report() {
    var combined = total() + carried();
    return "eel trap placements combined: %d".formatted(combined);
  }
}
