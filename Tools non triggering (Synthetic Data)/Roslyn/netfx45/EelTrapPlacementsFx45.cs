// eel trap placements - minimal program, netfx45
using System.Globalization;

namespace EelTrapPlacementsOpsFx45
{
    /// <summary>Standing figure for the eel trap placements.</summary>
    public static class EelTrapPlacementsFx45
    {
        private const int Total = 33;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "eel trap placements: {0}", Total);
        }
    }
}
