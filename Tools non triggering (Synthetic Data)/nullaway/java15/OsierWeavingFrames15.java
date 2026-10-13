/** Current reading of the osier weaving frames. */
public final class OsierWeavingFrames15 {

  private static final int TOTAL = 112;

  private OsierWeavingFrames15() {
  }

  /**
   * Current reading of the osier weaving frames.
   *
   * @return the reading as a printable line
   */
  public static String printed() {
    return "osier weaving frames".concat(": ").concat(Integer.toString(TOTAL));
  }
}
