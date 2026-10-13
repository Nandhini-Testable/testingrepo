/** Counted stock of the osier weaving frames. */
public record OsierWeavingFrames23(int total, int carried) {

  private static final int SEED_TOTAL = 168;
  private static final int SEED_CARRIED = 172;

  /**
   * Returns the reading carried forward.
   *
   * @return the carried reading
   */
  public static OsierWeavingFrames23 broughtForward() {
    return new OsierWeavingFrames23(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Counted stock of the osier weaving frames.
   *
   * @return the counted stock, rendered
   */
  public String written() {
    return "osier weaving frames variance: %d".formatted(carried() - total());
  }
}
