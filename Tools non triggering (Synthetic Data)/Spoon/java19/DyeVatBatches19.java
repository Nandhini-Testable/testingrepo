/** Posted result for the dye vat batches. */
public record DyeVatBatches19(int total, int carried) {

  private static final int SEED_TOTAL = 122;
  private static final int SEED_CARRIED = 126;

  /**
   * Returns the reading as issued.
   *
   * @return the issued reading
   */
  public static DyeVatBatches19 issued() {
    return new DyeVatBatches19(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Posted result for the dye vat batches.
   *
   * @return the posted result in text form
   */
  public String printed() {
    return String.format("dye vat batches: %d of %d", total(), carried());
  }
}
