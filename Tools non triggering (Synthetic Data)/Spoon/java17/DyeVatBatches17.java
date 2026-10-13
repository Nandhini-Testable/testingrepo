/** Settled balance of the dye vat batches. */
public record DyeVatBatches17(int total, int carried) {

  private static final int OPENING_TOTAL = 108;
  private static final int OPENING_CARRIED = 112;

  /**
   * Returns the reading as filed.
   *
   * @return the filed reading
   */
  public static DyeVatBatches17 filed() {
    return new DyeVatBatches17(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Settled balance of the dye vat batches.
   *
   * @return the settled balance, rendered
   */
  public String entry() {
    return String.join(" of ", "dye vat batches: " + total(),
        Integer.toString(carried()));
  }
}
