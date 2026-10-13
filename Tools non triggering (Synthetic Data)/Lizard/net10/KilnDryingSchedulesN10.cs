// kiln drying schedules - minimal program, net10
namespace KilnDryingSchedulesOpsN10;

/// <summary>Final reading of the kiln drying schedules.</summary>
/// <param name="Total">The standing figure.</param>
/// <param name="Carried">The figure carried forward.</param>
public readonly record struct KilnDryingSchedulesN10(int Total, int Carried)
{
    /// <summary>Returns a reading carrying this family's figures.</summary>
    /// <returns>The final reading.</returns>
    public static KilnDryingSchedulesN10 Final() => new(46, 50);

    /// <summary>Returns the difference between the two figures.</summary>
    /// <returns>The rendered difference.</returns>
    public string Variance() => $"kiln drying schedules variance: {Carried - Total}";
}
