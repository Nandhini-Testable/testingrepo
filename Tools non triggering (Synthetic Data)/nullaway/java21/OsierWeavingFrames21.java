/** Shift summary for the osier weaving frames. */
public record OsierWeavingFrames21(int total, int carried) {

  private static final int OPENING_TOTAL = 154;
  private static final int OPENING_CARRIED = 158;

  /**
   * Returns the reading after audit.
   *
   * @return the audited reading
   */
  public static OsierWeavingFrames21 audited() {
    return new OsierWeavingFrames21(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Shift summary for the osier weaving frames.
   *
   * @return the summary in one line
   */
  public String summarise() {
    return String.join(" of ", "osier weaving frames: " + total(),
        Integer.toString(carried()));
  }
}
