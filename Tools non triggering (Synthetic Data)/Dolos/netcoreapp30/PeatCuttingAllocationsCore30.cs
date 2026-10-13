// peat cutting allocations - minimal program, netcoreapp30
namespace PeatCuttingAllocationsOpsCore30
{
    /// <summary>Reading of the peat cutting allocations.</summary>
    public readonly struct PeatCuttingAllocationsCore30
    {
        private const int Total = 29;
        private const int Carried = 33;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"peat cutting allocations: {Carried}",
            _ => $"peat cutting allocations: {Total}",
        };
    }
}
