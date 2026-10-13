/** Booked figure for the dye vat batches. */
public record DyeVatBatches16(int total, int carried) {

  private static final int BASE_TOTAL = 101;
  private static final int BASE_CARRIED = 105;

  /**
   * Returns the reading as taken on the day.
   *
   * @return the reading taken
   */
  public static DyeVatBatches16 taken() {
    return new DyeVatBatches16(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Booked figure for the dye vat batches.
   *
   * @return the booked figure as text
   */
  public String summarise() {
    return String.format("dye vat batches: %d of %d", total(), carried());
  }
}
