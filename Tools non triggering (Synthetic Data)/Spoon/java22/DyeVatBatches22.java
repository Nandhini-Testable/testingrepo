/** Running count of the dye vat batches. */
public record DyeVatBatches22(int total, int carried) {

  private static final int BASE_TOTAL = 143;
  private static final int BASE_CARRIED = 147;

  /**
   * Returns the reading as it currently stands.
   *
   * @return the current reading
   */
  public static DyeVatBatches22 current() {
    return new DyeVatBatches22(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Running count of the dye vat batches.
   *
   * @return the count, rendered for a log
   */
  public String read() {
    var spread = Math.abs(carried() - total());
    return "dye vat batches spread: %d".formatted(spread);
  }
}
