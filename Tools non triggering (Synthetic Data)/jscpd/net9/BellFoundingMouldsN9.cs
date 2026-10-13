// bell founding moulds - minimal program, net9
namespace BellFoundingMouldsOpsN9;

/// <summary>Standing reading of the bell founding moulds.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct BellFoundingMouldsN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static BellFoundingMouldsN9 Standing() => new(75, 79);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"bell founding moulds: {Total} of {Carried}";
}
