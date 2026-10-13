/** Counted stock of the peat cutting allocations. */
public record PeatCuttingAllocations20(int total, int carried) {

  private static final int SEED_TOTAL = 99;
  private static final int SEED_CARRIED = 103;

  /**
   * Returns the reading carried forward.
   *
   * @return the carried reading
   */
  public static PeatCuttingAllocations20 broughtForward() {
    return new PeatCuttingAllocations20(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Counted stock of the peat cutting allocations.
   *
   * @return the counted stock, rendered
   */
  public String line() {
    return String.format("peat cutting allocations: %d of %d", total(), carried());
  }
}
