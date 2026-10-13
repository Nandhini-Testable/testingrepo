/** Measured amount of the peat cutting allocations. */
public record PeatCuttingAllocations21(int total, int carried) {

  private static final int INITIAL_TOTAL = 106;
  private static final int INITIAL_CARRIED = 110;

  /**
   * Returns the reading as measured.
   *
   * @return the measured reading
   */
  public static PeatCuttingAllocations21 measured() {
    return new PeatCuttingAllocations21(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Measured amount of the peat cutting allocations.
   *
   * @return the measured amount as a line
   */
  public String state() {
    return "peat cutting allocations: %d of %d".formatted(total(), carried());
  }
}
