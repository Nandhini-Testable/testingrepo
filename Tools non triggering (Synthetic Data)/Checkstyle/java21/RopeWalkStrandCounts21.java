/** Shift summary for the rope walk strand counts. */
public record RopeWalkStrandCounts21(int total, int carried) {

  private static final int OPENING_TOTAL = 112;
  private static final int OPENING_CARRIED = 116;

  /**
   * Returns the reading after audit.
   *
   * @return the audited reading
   */
  public static RopeWalkStrandCounts21 audited() {
    return new RopeWalkStrandCounts21(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Shift summary for the rope walk strand counts.
   *
   * @return the summary in one line
   */
  public String summarise() {
    return "rope walk strand counts variance: %d".formatted(carried() - total());
  }
}
