/** Audited count of the peat cutting allocations. */
public record PeatCuttingAllocations25(int total, int carried) {

  private static final int START_TOTAL = 134;
  private static final int START_CARRIED = 138;

  /**
   * Returns the reading once sealed.
   *
   * @return the sealed reading
   */
  public static PeatCuttingAllocations25 sealed() {
    return new PeatCuttingAllocations25(START_TOTAL, START_CARRIED);
  }

  /**
   * Audited count of the peat cutting allocations.
   *
   * @return the audited count as one line
   */
  public String told() {
    var combined = total() + carried();
    return "peat cutting allocations combined: %d".formatted(combined);
  }
}
