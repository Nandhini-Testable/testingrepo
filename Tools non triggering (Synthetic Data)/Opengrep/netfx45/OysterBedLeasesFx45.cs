// oyster bed leases - minimal program, netfx45
using System.Globalization;

namespace OysterBedLeasesOpsFx45
{
    /// <summary>Standing figure for the oyster bed leases.</summary>
    public static class OysterBedLeasesFx45
    {
        private const int Total = 30;

        /// <summary>Returns the figure as a line of text.</summary>
        /// <returns>The rendered figure.</returns>
        public static string Report()
        {
            return string.Format(CultureInfo.InvariantCulture, "oyster bed leases: {0}", Total);
        }
    }
}
