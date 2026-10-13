/** Booked figure for the eel trap placements. */
public record EelTrapPlacements19(int total, int carried) {

  private static final int BASE_TOTAL = 110;
  private static final int BASE_CARRIED = 114;

  /**
   * Returns the reading as taken on the day.
   *
   * @return the reading taken
   */
  public static EelTrapPlacements19 taken() {
    return new EelTrapPlacements19(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Booked figure for the eel trap placements.
   *
   * @return the booked figure as text
   */
  public String state() {
    return String.format("eel trap placements: %d of %d", total(), carried());
  }
}
