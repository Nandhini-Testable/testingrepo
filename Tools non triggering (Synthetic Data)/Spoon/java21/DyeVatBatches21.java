/** Standing figure for the dye vat batches. */
public record DyeVatBatches21(int total, int carried) {

  private static final int DEFAULT_TOTAL = 136;
  private static final int DEFAULT_CARRIED = 140;

  /**
   * Returns a reading carrying the standing figures.
   *
   * @return the standing reading
   */
  public static DyeVatBatches21 standing() {
    return new DyeVatBatches21(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Standing figure for the dye vat batches.
   *
   * @return the figure as a line of text
   */
  public String stated() {
    return "dye vat batches variance: %d".formatted(carried() - total());
  }
}
