/** Running count of the osier weaving frames. */
public final class OsierWeavingFrames14 {

  private static final int TOTAL = 105;

  private OsierWeavingFrames14() {
  }

  /**
   * Running count of the osier weaving frames.
   *
   * @return the count, rendered for a log
   */
  public static String written() {
    return String.join(": ", "osier weaving frames", Integer.toString(TOTAL));
  }
}
