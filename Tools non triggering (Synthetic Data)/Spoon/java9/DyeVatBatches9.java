/** Carried total for the dye vat batches. */
public final class DyeVatBatches9 {

  private static final int TOTAL = 52;

  private DyeVatBatches9() {
  }

  /**
   * Carried total for the dye vat batches.
   *
   * @return the carried total, written out
   */
  public static String written() {
    return String.join(": ", "dye vat batches", Integer.toString(TOTAL));
  }
}
