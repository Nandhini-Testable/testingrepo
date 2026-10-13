/** Final reading of the dye vat batches. */
public record DyeVatBatches20(int total, int carried) {

  private static final int INITIAL_TOTAL = 129;
  private static final int INITIAL_CARRIED = 133;

  /**
   * Returns the reading as drawn up.
   *
   * @return the drawn reading
   */
  public static DyeVatBatches20 drawn() {
    return new DyeVatBatches20(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Final reading of the dye vat batches.
   *
   * @return the final reading as a line
   */
  public String noted() {
    return String.join(" of ", "dye vat batches: " + total(),
        Integer.toString(carried()));
  }
}
