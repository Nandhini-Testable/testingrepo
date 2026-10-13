// oyster bed leases - minimal program, net9
namespace OysterBedLeasesOpsN9;

/// <summary>Standing reading of the oyster bed leases.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct OysterBedLeasesN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static OysterBedLeasesN9 Standing() => new(51, 55);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"oyster bed leases: {Total} of {Carried}";
}
