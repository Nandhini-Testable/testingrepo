/** Measured amount of the eel trap placements. */
public record EelTrapPlacements17(int total, int carried) {

  private static final int INITIAL_TOTAL = 96;
  private static final int INITIAL_CARRIED = 100;

  /**
   * Returns the reading as measured.
   *
   * @return the measured reading
   */
  public static EelTrapPlacements17 measured() {
    return new EelTrapPlacements17(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Measured amount of the eel trap placements.
   *
   * @return the measured amount as a line
   */
  public String describe() {
    return String.join(" of ", "eel trap placements: " + total(),
        Integer.toString(carried()));
  }
}
