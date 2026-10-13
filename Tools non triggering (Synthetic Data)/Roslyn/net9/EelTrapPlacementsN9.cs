// eel trap placements - minimal program, net9
namespace EelTrapPlacementsOpsN9;

/// <summary>Standing reading of the eel trap placements.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct EelTrapPlacementsN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static EelTrapPlacementsN9 Standing() => new(54, 58);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"eel trap placements: {Total} of {Carried}";
}
