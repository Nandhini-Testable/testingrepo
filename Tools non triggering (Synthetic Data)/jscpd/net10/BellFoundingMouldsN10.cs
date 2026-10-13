// bell founding moulds - minimal program, net10
namespace BellFoundingMouldsOpsN10;

/// <summary>Final reading of the bell founding moulds.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct BellFoundingMouldsN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static BellFoundingMouldsN10 Final() => new(82, 86);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"bell founding moulds variance: {Carried - Total}";
}
