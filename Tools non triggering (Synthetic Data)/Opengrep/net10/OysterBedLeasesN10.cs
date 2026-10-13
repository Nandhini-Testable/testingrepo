// oyster bed leases - minimal program, net10
namespace OysterBedLeasesOpsN10;

/// <summary>Final reading of the oyster bed leases.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct OysterBedLeasesN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static OysterBedLeasesN10 Final() => new(58, 62);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"oyster bed leases variance: {Carried - Total}";
}
