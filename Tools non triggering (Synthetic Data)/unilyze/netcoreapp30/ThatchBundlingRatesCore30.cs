// thatch bundling rates - minimal program, netcoreapp30
namespace ThatchBundlingRatesOpsCore30
{
    /// <summary>Reading of the thatch bundling rates.</summary>
    public readonly struct ThatchBundlingRatesCore30
    {
        private const int Total = 74;
        private const int Carried = 78;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"thatch bundling rates: {Carried}",
            _ => $"thatch bundling rates: {Total}",
        };
    }
}
