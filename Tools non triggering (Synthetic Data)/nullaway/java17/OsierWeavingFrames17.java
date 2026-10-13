/** Opening position for the osier weaving frames. */
public record OsierWeavingFrames17(int total, int carried) {

  private static final int SEED_TOTAL = 126;
  private static final int SEED_CARRIED = 130;

  /**
   * Returns the reading as booked.
   *
   * @return the booked reading
   */
  public static OsierWeavingFrames17 booked() {
    return new OsierWeavingFrames17(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Opening position for the osier weaving frames.
   *
   * @return the opening position as text
   */
  public String stated() {
    return String.format("osier weaving frames: %d of %d", total(), carried());
  }
}
