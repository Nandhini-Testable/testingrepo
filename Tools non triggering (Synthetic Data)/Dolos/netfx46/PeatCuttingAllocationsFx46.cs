// peat cutting allocations - minimal program, netfx46
namespace PeatCuttingAllocationsOpsFx46
{
    /// <summary>Running count of the peat cutting allocations.</summary>
    public static class PeatCuttingAllocationsFx46
    {
        private const int Total = 22;

        /// <summary>Returns the count as a line of text.</summary>
        /// <returns>The rendered count.</returns>
        public static string Describe() => $"peat cutting allocations: {Total}";
    }
}
