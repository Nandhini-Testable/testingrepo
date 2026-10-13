// kiln drying schedules - minimal program, netcoreapp30
namespace KilnDryingSchedulesOpsCore30
{
    /// <summary>Reading of the kiln drying schedules.</summary>
    public readonly struct KilnDryingSchedulesCore30
    {
        private const int Total = 32;
        private const int Carried = 36;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"kiln drying schedules: {Carried}",
            _ => $"kiln drying schedules: {Total}",
        };
    }
}
