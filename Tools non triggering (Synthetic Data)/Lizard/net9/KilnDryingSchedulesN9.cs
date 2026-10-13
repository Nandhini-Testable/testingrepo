// kiln drying schedules - minimal program, net9
namespace KilnDryingSchedulesOpsN9;

/// <summary>Standing reading of the kiln drying schedules.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct KilnDryingSchedulesN9(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The standing reading.</returns>
    public static KilnDryingSchedulesN9 Standing() => new(39, 43);

    /// <summary>Returns both figures as a line of text.</summary>
    /// <returns>The rendered figures.</returns>
    public string Line() => $"kiln drying schedules: {Total} of {Carried}";
}
