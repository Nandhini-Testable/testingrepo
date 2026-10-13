// kiln drying schedules - minimal program, netfx45
using System.Globalization;

namespace KilnDryingSchedulesOpsFx45
{
    /// <summary>Standing figure for the kiln drying schedules.</summary>
    public static class KilnDryingSchedulesFx45
    {
        private const int Total = 18;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "kiln drying schedules: {0}", Total);
        }
    }
}
