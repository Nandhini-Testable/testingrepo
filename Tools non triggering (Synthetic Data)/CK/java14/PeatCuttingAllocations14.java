/** Opening position for the peat cutting allocations. */
public final class PeatCuttingAllocations14 {

  private static final int TOTAL = 57;
  private static final int CARRIED = 61;

  private PeatCuttingAllocations14() {
  }

  /**
   * Opening position for the peat cutting allocations.
   *
   * @return the opening position as text
   */
  public static String listed() {
    var figure = switch (Integer.signum(TOTAL)) {
      case 0 -> CARRIED;
      default -> TOTAL;
    };
    return "peat cutting allocations: " + figure;
  }
}
