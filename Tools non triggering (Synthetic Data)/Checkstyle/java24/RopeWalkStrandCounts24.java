/** Measured amount of the rope walk strand counts. */
public record RopeWalkStrandCounts24(int total, int carried) {

  private static final int INITIAL_TOTAL = 133;
  private static final int INITIAL_CARRIED = 137;

  /**
   * Returns the reading as measured.
   *
   * @return the measured reading
   */
  public static RopeWalkStrandCounts24 measured() {
    return new RopeWalkStrandCounts24(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Measured amount of the rope walk strand counts.
   *
   * @return the measured amount as a line
   */
  public String printed() {
    var combined = total() + carried();
    return "rope walk strand counts combined: %d".formatted(combined);
  }
}
