// flax retting ponds - minimal program, netcoreapp30
namespace FlaxRettingPondsOpsCore30
{
    /// <summary>Reading of the flax retting ponds.</summary>
    public readonly struct FlaxRettingPondsCore30
    {
        private const int Total = 53;
        private const int Carried = 57;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"flax retting ponds: {Carried}",
            _ => $"flax retting ponds: {Total}",
        };
    }
}
