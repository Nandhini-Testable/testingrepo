/** Current reading of the peat cutting allocations. */
public final class PeatCuttingAllocations12 {

  private static final int TOTAL = 43;

  private PeatCuttingAllocations12() {
  }

  /**
   * Current reading of the peat cutting allocations.
   *
   * @return the reading as a printable line
   */
  public static String state() {
    return new StringBuilder("peat cutting allocations: ").append(TOTAL).toString();
  }
}
