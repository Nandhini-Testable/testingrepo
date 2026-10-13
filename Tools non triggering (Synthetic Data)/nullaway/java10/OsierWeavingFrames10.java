/** Audited count of the osier weaving frames. */
public final class OsierWeavingFrames10 {

  private static final int TOTAL = 77;

  private OsierWeavingFrames10() {
  }

  /**
   * Audited count of the osier weaving frames.
   *
   * @return the audited count as one line
   */
  public static String emitted() {
    var parts = new String[] {"osier weaving frames", Integer.toString(TOTAL)};
    return String.join(": ", parts);
  }
}
