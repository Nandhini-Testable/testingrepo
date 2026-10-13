/** Logged entry for the rope walk strand counts. */
public record RopeWalkStrandCounts25(int total, int carried) {

  private static final int DEFAULT_TOTAL = 140;
  private static final int DEFAULT_CARRIED = 144;

  /**
   * Returns the reading as recorded.
   *
   * @return the recorded reading
   */
  public static RopeWalkStrandCounts25 recorded() {
    return new RopeWalkStrandCounts25(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Logged entry for the rope walk strand counts.
   *
   * @return the logged entry in written form
   */
  public String noted() {
    return "rope walk strand counts: " + total() + " of " + carried();
  }
}
