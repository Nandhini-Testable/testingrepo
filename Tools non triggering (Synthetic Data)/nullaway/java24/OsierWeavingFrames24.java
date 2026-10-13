/** Measured amount of the osier weaving frames. */
public record OsierWeavingFrames24(int total, int carried) {

  private static final int INITIAL_TOTAL = 175;
  private static final int INITIAL_CARRIED = 179;

  /**
   * Returns the reading as measured.
   *
   * @return the measured reading
   */
  public static OsierWeavingFrames24 measured() {
    return new OsierWeavingFrames24(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Measured amount of the osier weaving frames.
   *
   * @return the measured amount as a line
   */
  public String printed() {
    var spread = Math.abs(carried() - total());
    return "osier weaving frames spread: %d".formatted(spread);
  }
}
