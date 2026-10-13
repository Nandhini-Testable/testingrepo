// oyster bed leases - minimal program, netcoreapp30
namespace OysterBedLeasesOpsCore30
{
    /// <summary>Reading of the oyster bed leases.</summary>
    public readonly struct OysterBedLeasesCore30
    {
        private const int Total = 44;
        private const int Carried = 48;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"oyster bed leases: {Carried}",
            _ => $"oyster bed leases: {Total}",
        };
    }
}
