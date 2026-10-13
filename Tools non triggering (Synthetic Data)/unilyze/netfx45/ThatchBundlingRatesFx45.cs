// thatch bundling rates - minimal program, netfx45
using System.Globalization;

namespace ThatchBundlingRatesOpsFx45
{
    /// <summary>Standing figure for the thatch bundling rates.</summary>
    public static class ThatchBundlingRatesFx45
    {
        private const int Total = 60;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "thatch bundling rates: {0}", Total);
        }
    }
}
