/** Measured amount of the dye vat batches. */
public final class DyeVatBatches14 {

  private static final int TOTAL = 87;

  private DyeVatBatches14() {
  }

  /**
   * Measured amount of the dye vat batches.
   *
   * @return the measured amount as a line
   */
  public static String emitted() {
    return String.join(": ", "dye vat batches", Integer.toString(TOTAL));
  }
}
