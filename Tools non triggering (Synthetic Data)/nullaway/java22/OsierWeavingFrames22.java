/** Recorded quantity of the osier weaving frames. */
public record OsierWeavingFrames22(int total, int carried) {

  private static final int START_TOTAL = 161;
  private static final int START_CARRIED = 165;

  /**
   * Returns the reading as posted.
   *
   * @return the posted reading
   */
  public static OsierWeavingFrames22 posted() {
    return new OsierWeavingFrames22(START_TOTAL, START_CARRIED);
  }

  /**
   * Recorded quantity of the osier weaving frames.
   *
   * @return the recorded quantity as text
   */
  public String entry() {
    return "osier weaving frames: %d of %d".formatted(total(), carried());
  }
}
