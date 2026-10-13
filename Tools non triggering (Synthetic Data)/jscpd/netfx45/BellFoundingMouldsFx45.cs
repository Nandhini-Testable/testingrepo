// bell founding moulds - minimal program, netfx45
using System.Globalization;

namespace BellFoundingMouldsOpsFx45
{
    /// <summary>Standing figure for the bell founding moulds.</summary>
    public static class BellFoundingMouldsFx45
    {
        private const int Total = 54;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "bell founding moulds: {0}", Total);
        }
    }
}
