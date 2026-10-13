/** Opening position for the dye vat batches. */
public record DyeVatBatches25(int total, int carried) {

  private static final int SEED_TOTAL = 164;
  private static final int SEED_CARRIED = 168;

  /**
   * Returns the reading as booked.
   *
   * @return the booked reading
   */
  public static DyeVatBatches25 booked() {
    return new DyeVatBatches25(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Opening position for the dye vat batches.
   *
   * @return the opening position as text
   */
  public String summarise() {
    return "dye vat batches: " + total() + " of " + carried();
  }
}
