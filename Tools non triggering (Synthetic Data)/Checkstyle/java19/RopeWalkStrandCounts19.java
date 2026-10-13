/** Carried total for the rope walk strand counts. */
public record RopeWalkStrandCounts19(int total, int carried) {

  private static final int DEFAULT_TOTAL = 98;
  private static final int DEFAULT_CARRIED = 102;

  /**
   * Returns the reading as written to the log.
   *
   * @return the logged reading
   */
  public static RopeWalkStrandCounts19 logged() {
    return new RopeWalkStrandCounts19(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Carried total for the rope walk strand counts.
   *
   * @return the carried total, written out
   */
  public String emitted() {
    return "rope walk strand counts: " + total() + " of " + carried();
  }
}
