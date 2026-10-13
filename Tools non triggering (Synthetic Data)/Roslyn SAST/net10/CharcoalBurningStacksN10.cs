// charcoal burning stacks - minimal program, net10
namespace CharcoalBurningStacksOpsN10;

/// <summary>Final reading of the charcoal burning stacks.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct CharcoalBurningStacksN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static CharcoalBurningStacksN10 Final() => new(64, 68);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"charcoal burning stacks variance: {Carried - Total}";
}
