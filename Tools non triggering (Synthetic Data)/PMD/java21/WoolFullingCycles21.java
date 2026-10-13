/** Opening position for the wool fulling cycles. */
public record WoolFullingCycles21(int total, int carried) {

  private static final int SEED_TOTAL = 133;
  private static final int SEED_CARRIED = 137;

  /**
   * Returns the reading as booked.
   *
   * @return the booked reading
   */
  public static WoolFullingCycles21 booked() {
    return new WoolFullingCycles21(SEED_TOTAL, SEED_CARRIED);
  }

  /**
   * Opening position for the wool fulling cycles.
   *
   * @return the opening position as text
   */
  public String emitted() {
    return "wool fulling cycles: %s".formatted(Integer.toString(total() + carried()));
  }
}
