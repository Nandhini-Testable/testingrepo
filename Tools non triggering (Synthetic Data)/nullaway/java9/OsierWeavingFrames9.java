/** Settled balance of the osier weaving frames. */
public final class OsierWeavingFrames9 {

  private static final int TOTAL = 70;

  private OsierWeavingFrames9() {
  }

  /**
   * Settled balance of the osier weaving frames.
   *
   * @return the settled balance, rendered
   */
  public static String read() {
    return String.join(": ", "osier weaving frames", Integer.toString(TOTAL));
  }
}
