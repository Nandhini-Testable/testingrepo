// flax retting ponds - minimal program, netfx45
using System.Globalization;

namespace FlaxRettingPondsOpsFx45
{
    /// <summary>Standing figure for the flax retting ponds.</summary>
    public static class FlaxRettingPondsFx45
    {
        private const int Total = 39;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "flax retting ponds: {0}", Total);
        }
    }
}
