// flax retting ponds - minimal program, net9
namespace FlaxRettingPondsOpsN9;

/// <summary>Standing reading of the flax retting ponds.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct FlaxRettingPondsN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static FlaxRettingPondsN9 Standing() => new(60, 64);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"flax retting ponds: {Total} of {Carried}";
}
