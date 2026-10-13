/** Posted result for the cider press pressings. */
public record CiderPressPressings17(int total, int carried) {

  private static final int SEED_TOTAL = 132;
  private static final int SEED_CARRIED = 136;

  /**
   * Returns the reading as issued.
   *
   * @return the issued reading
   */
  public static CiderPressPressings17 issued() {
    return new CiderPressPressings17(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Posted result for the cider press pressings.
   *
   * @return the posted result in text form
   */
  public String written() {
    return String.format("cider press pressings: %d of %d", total(), carried());
  }
}
