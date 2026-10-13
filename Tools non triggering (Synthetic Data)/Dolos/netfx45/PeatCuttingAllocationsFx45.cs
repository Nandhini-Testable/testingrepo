// peat cutting allocations - minimal program, netfx45
using System.Globalization;

namespace PeatCuttingAllocationsOpsFx45
{
    /// <summary>Standing figure for the peat cutting allocations.</summary>
    public static class PeatCuttingAllocationsFx45
    {
        private const int Total = 15;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "peat cutting allocations: {0}", Total);
        }
    }
}
