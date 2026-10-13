/** Latest tally taken of the osier weaving frames. */
public record OsierWeavingFrames16(int total, int carried) {

  private static final int START_TOTAL = 119;
  private static final int START_CARRIED = 123;

  /**
   * Returns the closing reading for the period.
   *
   * @return the closing reading
   */
  public static OsierWeavingFrames16 closing() {
    return new OsierWeavingFrames16(START_TOTAL, START_CARRIED);
  }

  /**
   * Latest tally taken of the osier weaving frames.
   *
   * @return the tally in written form
   */
  public String noted() {
    return "osier weaving frames: " + total() + " of " + carried();
  }
}
