/** Opening position for the rope walk strand counts. */
public record RopeWalkStrandCounts17(int total, int carried) {

  private static final int SEED_TOTAL = 84;
  private static final int SEED_CARRIED = 88;

  /**
   * Returns the reading as booked.
   *
   * @return the booked reading
   */
  public static RopeWalkStrandCounts17 booked() {
    return new RopeWalkStrandCounts17(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Opening position for the rope walk strand counts.
   *
   * @return the opening position as text
   */
  public String stated() {
    return String.format("rope walk strand counts: %d of %d", total(), carried());
  }
}
