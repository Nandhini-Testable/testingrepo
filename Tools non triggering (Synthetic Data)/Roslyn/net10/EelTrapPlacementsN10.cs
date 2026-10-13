// eel trap placements - minimal program, net10
namespace EelTrapPlacementsOpsN10;

/// <summary>Final reading of the eel trap placements.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct EelTrapPlacementsN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static EelTrapPlacementsN10 Final() => new(61, 65);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"eel trap placements variance: {Carried - Total}";
}
