/** Counted stock of the dye vat batches. */
public final class DyeVatBatches13 {

  private static final int TOTAL = 80;

  private DyeVatBatches13() {
  }

  /**
   * Counted stock of the dye vat batches.
   *
   * @return the counted stock, rendered
   */
  public static String read() {
    return "dye vat batches".concat(": ").concat(Integer.toString(TOTAL));
  }
}
