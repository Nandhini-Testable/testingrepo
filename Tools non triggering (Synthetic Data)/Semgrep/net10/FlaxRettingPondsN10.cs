// flax retting ponds - minimal program, net10
namespace FlaxRettingPondsOpsN10;

/// <summary>Final reading of the flax retting ponds.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct FlaxRettingPondsN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static FlaxRettingPondsN10 Final() => new(67, 71);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"flax retting ponds variance: {Carried - Total}";
}
