// peat cutting allocations - minimal program, net9
namespace PeatCuttingAllocationsOpsN9;

/// <summary>Standing reading of the peat cutting allocations.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct PeatCuttingAllocationsN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static PeatCuttingAllocationsN9 Standing() => new(36, 40);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"peat cutting allocations: {Total} of {Carried}";
}
