/** Counted stock of the eel trap placements. */
public record EelTrapPlacements16(int total, int carried) {

  private static final int SEED_TOTAL = 89;
  private static final int SEED_CARRIED = 93;

  /**
   * Returns the reading carried forward.
   *
   * @return the carried reading
   */
  public static EelTrapPlacements16 broughtForward() {
    return new EelTrapPlacements16(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Counted stock of the eel trap placements.
   *
   * @return the counted stock, rendered
   */
  public String report() {
    return String.format("eel trap placements: %d of %d", total(), carried());
  }
}
