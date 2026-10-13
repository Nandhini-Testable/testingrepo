/** Reconciled figure for the osier weaving frames. */
public record OsierWeavingFrames20(int total, int carried) {

  private static final int BASE_TOTAL = 147;
  private static final int BASE_CARRIED = 151;

  /**
   * Returns the reading once reconciled.
   *
   * @return the settled reading
   */
  public static OsierWeavingFrames20 settled() {
    return new OsierWeavingFrames20(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Reconciled figure for the osier weaving frames.
   *
   * @return the reconciled figure as a line
   */
  public String render() {
    return String.format("osier weaving frames: %d of %d", total(), carried());
  }
}
