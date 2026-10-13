/** Latest tally taken of the peat cutting allocations. */
public final class PeatCuttingAllocations13 {

  private static final int TOTAL = 50;

  private PeatCuttingAllocations13() {
  }

  /**
   * Latest tally taken of the peat cutting allocations.
   *
   * @return the tally in written form
   */
  public static String shown() {
    return String.join(": ", "peat cutting allocations", Integer.toString(TOTAL));
  }
}
