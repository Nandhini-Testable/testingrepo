/** Closing position for the osier weaving frames. */
public record OsierWeavingFrames18(int total, int carried) {

  private static final int INITIAL_TOTAL = 133;
  private static final int INITIAL_CARRIED = 137;

  /**
   * Returns the reading as counted by hand.
   *
   * @return the counted reading
   */
  public static OsierWeavingFrames18 counted() {
    return new OsierWeavingFrames18(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Closing position for the osier weaving frames.
   *
   * @return the closing position as text
   */
  public String read() {
    return String.join(" of ", "osier weaving frames: " + total(),
        Integer.toString(carried()));
  }
}
