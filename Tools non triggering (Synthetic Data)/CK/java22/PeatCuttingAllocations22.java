/** Logged entry for the peat cutting allocations. */
public record PeatCuttingAllocations22(int total, int carried) {

  private static final int DEFAULT_TOTAL = 113;
  private static final int DEFAULT_CARRIED = 117;

  /**
   * Returns the reading as recorded.
   *
   * @return the recorded reading
   */
  public static PeatCuttingAllocations22 recorded() {
    return new PeatCuttingAllocations22(DEFAULT_TOTAL, DEFAULT_CARRIED);
  }

  /**
   * Logged entry for the peat cutting allocations.
   *
   * @return the logged entry in written form
   */
  public String shown() {
    return "peat cutting allocations variance: %d".formatted(carried() - total());
  }
}
