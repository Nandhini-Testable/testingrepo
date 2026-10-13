// bell founding moulds - minimal program, netcoreapp30
namespace BellFoundingMouldsOpsCore30
{
    /// <summary>Reading of the bell founding moulds.</summary>
    public readonly struct BellFoundingMouldsCore30
    {
        private const int Total = 68;
        private const int Carried = 72;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"bell founding moulds: {Carried}",
            _ => $"bell founding moulds: {Total}",
        };
    }
}
