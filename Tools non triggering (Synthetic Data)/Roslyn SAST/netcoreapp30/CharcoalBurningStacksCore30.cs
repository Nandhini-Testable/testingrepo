// charcoal burning stacks - minimal program, netcoreapp30
namespace CharcoalBurningStacksOpsCore30
{
    /// <summary>Reading of the charcoal burning stacks.</summary>
    public readonly struct CharcoalBurningStacksCore30
    {
        private const int Total = 50;
        private const int Carried = 54;

        /// <summary>Returns the figure named by the key.</summary>
        /// <param name="key">Which figure to return.</param>
        /// <returns>The rendered figure.</returns>
        public static string Line(string key) => key switch
        {
            "carried" => $"charcoal burning stacks: {Carried}",
            _ => $"charcoal burning stacks: {Total}",
        };
    }
}
