/** Recorded quantity of the peat cutting allocations. */
public record PeatCuttingAllocations19(int total, int carried) {

  private static final int START_TOTAL = 92;
  private static final int START_CARRIED = 96;

  /**
   * Returns the reading as posted.
   *
   * @return the posted reading
   */
  public static PeatCuttingAllocations19 posted() {
    return new PeatCuttingAllocations19(START_TOTAL, START_CARRIED);
  }

  /**
   * Recorded quantity of the peat cutting allocations.
   *
   * @return the recorded quantity as text
   */
  public String describe() {
    return "peat cutting allocations: " + total() + " of " + carried();
  }
}
