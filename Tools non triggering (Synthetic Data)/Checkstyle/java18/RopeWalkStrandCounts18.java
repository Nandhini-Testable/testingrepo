/** Closing position for the rope walk strand counts. */
public record RopeWalkStrandCounts18(int total, int carried) {

  private static final int INITIAL_TOTAL = 91;
  private static final int INITIAL_CARRIED = 95;

  /**
   * Returns the reading as counted by hand.
   *
   * @return the counted reading
   */
  public static RopeWalkStrandCounts18 counted() {
    return new RopeWalkStrandCounts18(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Closing position for the rope walk strand counts.
   *
   * @return the closing position as text
   */
  public String read() {
    return String.join(" of ", "rope walk strand counts: " + total(),
        Integer.toString(carried()));
  }
}
