/** Final reading of the peat cutting allocations. */
public final class PeatCuttingAllocations9 {

  private static final int TOTAL = 22;

  private PeatCuttingAllocations9() {
  }

  /**
   * Final reading of the peat cutting allocations.
   *
   * @return the final reading as a line
   */
  public static String report() {
    return String.join(": ", "peat cutting allocations", Integer.toString(TOTAL));
  }
}
