// eel trap placements - minimal program, netcoreapp30
namespace EelTrapPlacementsOpsCore30
{
    /// <summary>Reading of the eel trap placements.</summary>
    public readonly struct EelTrapPlacementsCore30
    {
        private const int Total = 47;
        private const int Carried = 51;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"eel trap placements: {Carried}",
            _ => $"eel trap placements: {Total}",
        };
    }
}
