/** Carried total for the osier weaving frames. */
public record OsierWeavingFrames19(int total, int carried) {

  private static final int DEFAULT_TOTAL = 140;
  private static final int DEFAULT_CARRIED = 144;

  /**
   * Returns the reading as written to the log.
   *
   * @return the logged reading
   */
  public static OsierWeavingFrames19 logged() {
    return new OsierWeavingFrames19(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Carried total for the osier weaving frames.
   *
   * @return the carried total, written out
   */
  public String emitted() {
    return "osier weaving frames: " + total() + " of " + carried();
  }
}
