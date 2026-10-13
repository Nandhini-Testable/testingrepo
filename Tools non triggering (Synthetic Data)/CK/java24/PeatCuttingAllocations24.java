/** Settled balance of the peat cutting allocations. */
public record PeatCuttingAllocations24(int total, int carried) {

  private static final int OPENING_TOTAL = 127;
  private static final int OPENING_CARRIED = 131;

  /**
   * Returns the reading as filed.
   *
   * @return the filed reading
   */
  public static PeatCuttingAllocations24 filed() {
    return new PeatCuttingAllocations24(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Settled balance of the peat cutting allocations.
   *
   * @return the settled balance, rendered
   */
  public String given() {
    return "peat cutting allocations: %s".formatted(Integer.toString(total() + carried()));
  }
}
