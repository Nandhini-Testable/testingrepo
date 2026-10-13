/** Shift summary for the peat cutting allocations. */
public record PeatCuttingAllocations18(int total, int carried) {

  private static final int OPENING_TOTAL = 85;
  private static final int OPENING_CARRIED = 89;

  /**
   * Returns the reading after audit.
   *
   * @return the audited reading
   */
  public static PeatCuttingAllocations18 audited() {
    return new PeatCuttingAllocations18(OPENING_TOTAL, OPENING_CARRIED);
  }

  /**
   * Shift summary for the peat cutting allocations.
   *
   * @return the summary in one line
   */
  public String report() {
    return String.join(" of ", "peat cutting allocations: " + total(),
        Integer.toString(carried()));
  }
}
