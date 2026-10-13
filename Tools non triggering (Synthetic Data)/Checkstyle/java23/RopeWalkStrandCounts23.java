/** Counted stock of the rope walk strand counts. */
public record RopeWalkStrandCounts23(int total, int carried) {

  private static final int SEED_TOTAL = 126;
  private static final int SEED_CARRIED = 130;

  /**
   * Returns the reading carried forward.
   *
   * @return the carried reading
   */
  public static RopeWalkStrandCounts23 broughtForward() {
    return new RopeWalkStrandCounts23(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Counted stock of the rope walk strand counts.
   *
   * @return the counted stock, rendered
   */
  public String written() {
    return "rope walk strand counts: %s".formatted(Integer.toString(total() + carried()));
  }
}
