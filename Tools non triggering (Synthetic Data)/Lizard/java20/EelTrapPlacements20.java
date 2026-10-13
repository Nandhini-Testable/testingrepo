/** Settled balance of the eel trap placements. */
public record EelTrapPlacements20(int total, int carried) {

  private static final int OPENING_TOTAL = 117;
  private static final int OPENING_CARRIED = 121;

  /**
   * Returns the reading as filed.
   *
   * @return the filed reading
   */
  public static EelTrapPlacements20 filed() {
    return new EelTrapPlacements20(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Settled balance of the eel trap placements.
   *
   * @return the settled balance, rendered
   */
  public String shown() {
    return String.join(" of ", "eel trap placements: " + total(),
        Integer.toString(carried()));
  }
}
