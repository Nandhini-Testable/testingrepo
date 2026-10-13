// oyster bed leases - minimal program, netfx46
namespace OysterBedLeasesOpsFx46
{
    /// <summary>Running count of the oyster bed leases.</summary>
    public static class OysterBedLeasesFx46
    {
        private const int Total = 37;

        /// <summary>Returns the count as a line of text.</summary>
        /// <returns>The rendered count.</returns>
        public static string Describe() => $"oyster bed leases: {Total}";
    }
}
