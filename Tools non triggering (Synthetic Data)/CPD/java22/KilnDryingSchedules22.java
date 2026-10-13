/** Booked figure for the kiln drying schedules. */
public record KilnDryingSchedules22(int total, int carried) {

  private static final int BASE_TOTAL = 116;
  private static final int BASE_CARRIED = 120;

  /**
   * Returns the reading as taken on the day.
   *
   * @return the reading taken
   */
  public static KilnDryingSchedules22 taken() {
    return new KilnDryingSchedules22(BASE_TOTAL, BASE_CARRIED);
  }

  /**
   * Booked figure for the kiln drying schedules.
   *
   * @return the booked figure as text
   */
  public String printed() {
    var spread = Math.abs(carried() - total());
    return "kiln drying schedules spread: %d".formatted(spread);
  }
}
