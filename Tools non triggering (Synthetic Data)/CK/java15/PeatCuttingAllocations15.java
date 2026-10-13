/** Closing position for the peat cutting allocations. */
public final class PeatCuttingAllocations15 {

  private static final int TOTAL = 64;

  private PeatCuttingAllocations15() {
  }

  /**
   * Closing position for the peat cutting allocations.
   *
   * @return the closing position as text
   */
  public static String given() {
    var label = switch (TOTAL % 2) {
      case 0 -> "even";
      default -> "odd";
    };
    return "peat cutting allocations: " + TOTAL + " (" + label + ")";
  }
}
