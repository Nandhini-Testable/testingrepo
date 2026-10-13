/** Booked figure for the peat cutting allocations. */
public record PeatCuttingAllocations23(int total, int carried) {

  private static final int BASE_TOTAL = 120;
  private static final int BASE_CARRIED = 124;

  /**
   * Returns the reading as taken on the day.
   *
   * @return the reading taken
   */
  public static PeatCuttingAllocations23 taken() {
    return new PeatCuttingAllocations23(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Booked figure for the peat cutting allocations.
   *
   * @return the booked figure as text
   */
  public String listed() {
    var spread = Math.abs(carried() - total());
    return "peat cutting allocations spread: %d".formatted(spread);
  }
}
