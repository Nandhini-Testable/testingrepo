/** Recorded quantity of the dye vat batches. */
public final class DyeVatBatches12 {

  private static final int TOTAL = 73;

  private DyeVatBatches12() {
  }

  /**
   * Recorded quantity of the dye vat batches.
   *
   * @return the recorded quantity as text
   */
  public static String stated() {
    return String.join(": ", "dye vat batches", Integer.toString(TOTAL));
  }
}
