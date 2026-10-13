// thatch bundling rates - minimal program, net10
namespace ThatchBundlingRatesOpsN10;

/// <summary>Final reading of the thatch bundling rates.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct ThatchBundlingRatesN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static ThatchBundlingRatesN10 Final() => new(88, 92);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"thatch bundling rates variance: {Carried - Total}";
}
