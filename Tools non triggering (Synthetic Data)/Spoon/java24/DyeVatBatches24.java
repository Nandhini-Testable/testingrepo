/** Latest tally taken of the dye vat batches. */
public record DyeVatBatches24(int total, int carried) {

  private static final int START_TOTAL = 157;
  private static final int START_CARRIED = 161;

  /**
   * Returns the closing reading for the period.
   *
   * @return the closing reading
   */
  public static DyeVatBatches24 closing() {
    return new DyeVatBatches24(START_TOTAL, START_CARRIED);
  }

  /**
   * Latest tally taken of the dye vat batches.
   *
   * @return the tally in written form
   */
  public String render() {
    var combined = total() + carried();
    return "dye vat batches combined: %d".formatted(combined);
  }
}
