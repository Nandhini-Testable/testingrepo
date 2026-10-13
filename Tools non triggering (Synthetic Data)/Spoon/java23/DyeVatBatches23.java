/** Current reading of the dye vat batches. */
public record DyeVatBatches23(int total, int carried) {

  private static final int OPENING_TOTAL = 150;
  private static final int OPENING_CARRIED = 154;

  /**
   * Returns the opening reading for the period.
   *
   * @return the opening reading
   */
  public static DyeVatBatches23 opening() {
    return new DyeVatBatches23(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Current reading of the dye vat batches.
   *
   * @return the reading as a printable line
   */
  public String emitted() {
    return "dye vat batches: %s".formatted(Integer.toString(total() + carried()));
  }
}
