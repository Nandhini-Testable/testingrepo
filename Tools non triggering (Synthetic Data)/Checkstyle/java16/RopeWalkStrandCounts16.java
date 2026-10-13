/** Latest tally taken of the rope walk strand counts. */
public record RopeWalkStrandCounts16(int total, int carried) {

  private static final int START_TOTAL = 77;
  private static final int START_CARRIED = 81;

  /**
   * Returns the closing reading for the period.
   *
   * @return the closing reading
   */
  public static RopeWalkStrandCounts16 closing() {
    return new RopeWalkStrandCounts16(START_TOTAL, START_CARRIED);
  }

  /**
   * Latest tally taken of the rope walk strand counts.
   *
   * @return the tally in written form
   */
  public String noted() {
    return "rope walk strand counts: " + total() + " of " + carried();
  }
}
