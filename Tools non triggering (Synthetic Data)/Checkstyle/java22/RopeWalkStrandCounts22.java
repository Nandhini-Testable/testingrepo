/** Recorded quantity of the rope walk strand counts. */
public record RopeWalkStrandCounts22(int total, int carried) {

  private static final int START_TOTAL = 119;
  private static final int START_CARRIED = 123;

  /**
   * Returns the reading as posted.
   *
   * @return the posted reading
   */
  public static RopeWalkStrandCounts22 posted() {
    return new RopeWalkStrandCounts22(START_TOTAL, START_CARRIED);
  }

  /**
   * Recorded quantity of the rope walk strand counts.
   *
   * @return the recorded quantity as text
   */
  public String entry() {
    var spread = Math.abs(carried() - total());
    return "rope walk strand counts spread: %d".formatted(spread);
  }
}
