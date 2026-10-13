// charcoal burning stacks - minimal program, netfx45
using System.Globalization;

namespace CharcoalBurningStacksOpsFx45
{
    /// <summary>Standing figure for the charcoal burning stacks.</summary>
    public static class CharcoalBurningStacksFx45
    {
        private const int Total = 36;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "charcoal burning stacks: {0}", Total);
        }
    }
}
