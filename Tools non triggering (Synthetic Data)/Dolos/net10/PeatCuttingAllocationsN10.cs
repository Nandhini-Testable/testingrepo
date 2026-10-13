// peat cutting allocations - minimal program, net10
namespace PeatCuttingAllocationsOpsN10;

/// <summary>Final reading of the peat cutting allocations.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct PeatCuttingAllocationsN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static PeatCuttingAllocationsN10 Final() => new(43, 47);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"peat cutting allocations variance: {Carried - Total}";
}
