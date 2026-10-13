/** Audited count of the dye vat batches. */
public record DyeVatBatches18(int total, int carried) {

  private static final int START_TOTAL = 115;
  private static final int START_CARRIED = 119;

  /**
   * Returns the reading once sealed.
   *
   * @return the sealed reading
   */
  public static DyeVatBatches18 sealed() {
    return new DyeVatBatches18(START_TOTAL, START_CARRIED);
  }

  /**
   * Audited count of the dye vat batches.
   *
   * @return the audited count as one line
   */
  public String written() {
    return "dye vat batches: " + total() + " of " + carried();
  }
}
