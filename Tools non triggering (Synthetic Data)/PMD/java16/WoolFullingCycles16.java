/** Final reading of the wool fulling cycles. */
public record WoolFullingCycles16(int total, int carried) {

  private static final int INITIAL_TOTAL = 98;
  private static final int INITIAL_CARRIED = 102;

  /**
   * Returns the reading as drawn up.
   *
   * @return the drawn reading
   */
  public static WoolFullingCycles16 drawn() {
    return new WoolFullingCycles16(INITIAL_TOTAL, INITIAL_CARRIED);
  }

  /**
   * Final reading of the wool fulling cycles.
   *
   * @return the final reading as a line
   */
  public String written() {
    return String.join(" of ", "wool fulling cycles: " + total(),
        Integer.toString(carried()));
  }
}
