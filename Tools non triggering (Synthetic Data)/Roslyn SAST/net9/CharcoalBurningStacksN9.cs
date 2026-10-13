// charcoal burning stacks - minimal program, net9
namespace CharcoalBurningStacksOpsN9;

/// <summary>Standing reading of the charcoal burning stacks.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct CharcoalBurningStacksN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static CharcoalBurningStacksN9 Standing() => new(57, 61);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"charcoal burning stacks: {Total} of {Carried}";
}
