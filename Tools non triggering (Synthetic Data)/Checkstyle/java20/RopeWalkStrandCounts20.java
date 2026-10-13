/** Reconciled figure for the rope walk strand counts. */
public record RopeWalkStrandCounts20(int total, int carried) {

  private static final int BASE_TOTAL = 105;
  private static final int BASE_CARRIED = 109;

  /**
   * Returns the reading once reconciled.
   *
   * @return the settled reading
   */
  public static RopeWalkStrandCounts20 settled() {
    return new RopeWalkStrandCounts20(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Reconciled figure for the rope walk strand counts.
   *
   * @return the reconciled figure as a line
   */
  public String render() {
    return String.format("rope walk strand counts: %d of %d", total(), carried());
  }
}
