/** Carried total for the peat cutting allocations. */
public record PeatCuttingAllocations16(int total, int carried) {

  private static final int DEFAULT_TOTAL = 71;
  private static final int DEFAULT_CARRIED = 75;

  /**
   * Returns the reading as written to the log.
   *
   * @return the logged reading
   */
  public static PeatCuttingAllocations16 logged() {
    return new PeatCuttingAllocations16(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Carried total for the peat cutting allocations.
   *
   * @return the carried total, written out
   */
  public String told() {
    return "peat cutting allocations: " + total() + " of " + carried();
  }
}
