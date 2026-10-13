/** Posted result for the eel trap placements. */
public record EelTrapPlacements22(int total, int carried) {

  private static final int SEED_TOTAL = 131;
  private static final int SEED_CARRIED = 135;

  /**
   * Returns the reading as issued.
   *
   * @return the issued reading
   */
  public static EelTrapPlacements22 issued() {
    return new EelTrapPlacements22(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Posted result for the eel trap placements.
   *
   * @return the posted result in text form
   */
  public String given() {
    return "eel trap placements variance: %d".formatted(carried() - total());
  }
}
