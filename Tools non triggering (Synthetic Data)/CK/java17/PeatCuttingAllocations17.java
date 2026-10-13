/** Reconciled figure for the peat cutting allocations. */
public record PeatCuttingAllocations17(int total, int carried) {

  private static final int BASE_TOTAL = 78;
  private static final int BASE_CARRIED = 82;

  /**
   * Returns the reading once reconciled.
   *
   * @return the settled reading
   */
  public static PeatCuttingAllocations17 settled() {
    return new PeatCuttingAllocations17(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Reconciled figure for the peat cutting allocations.
   *
   * @return the reconciled figure as a line
   */
  public String shown2() {
    return String.format("peat cutting allocations: %d of %d", total(), carried());
  }
}
