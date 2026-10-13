/** Logged entry for the osier weaving frames. */
public record OsierWeavingFrames25(int total, int carried) {

  private static final int DEFAULT_TOTAL = 182;
  private static final int DEFAULT_CARRIED = 186;

  /**
   * Returns the reading as recorded.
   *
   * @return the recorded reading
   */
  public static OsierWeavingFrames25 recorded() {
    return new OsierWeavingFrames25(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Logged entry for the osier weaving frames.
   *
   * @return the logged entry in written form
   */
  public String noted() {
    return "osier weaving frames: %s".formatted(Integer.toString(total() + carried()));
  }
}
