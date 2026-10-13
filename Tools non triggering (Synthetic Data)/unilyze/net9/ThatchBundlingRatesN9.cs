// thatch bundling rates - minimal program, net9
namespace ThatchBundlingRatesOpsN9;

/// <summary>Standing reading of the thatch bundling rates.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct ThatchBundlingRatesN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static ThatchBundlingRatesN9 Standing() => new(81, 85);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"thatch bundling rates: {Total} of {Carried}";
}
