// Tool Triggering (Synthetic Data) -- Coverlet
//
// PLANTED: a branch-dense target where statement and branch coverage genuinely differ
// EXPECTED: a statement-coverage and a branch-coverage number that are not equal
//
// Family net5.0, C# 9.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 9.0 (it does not compile under C# 8.0).

using System;

namespace OrderKit.ToolData.Coverlet
{

    /// <summary>Statement- and branch-coverage target. Every branch below is
    /// reachable, and no two branches are equivalent, so a statement-coverage
    /// number and a branch-coverage number genuinely differ on this fixture --
    /// which is what makes Coverlet's two headline metrics distinguishable
    /// instead of identical.</summary>
    public static class BranchyCalculator
    {
        public static long Net(long gross, int qty, bool taxable, string region)
        {
            long net = gross;
            if (qty <= 0) return 0;
            if (qty > 100) net = net - (net / 10);
            if (taxable) net = net + (net / 8);
            if (region == "eu") net = net + 120;
            else if (region == "apac") net = net + 220;
            if (net < 0) net = 0;
            return net;
        }

        public static bool Eligible(int score, bool verified)
        {
            if (score >= 700 && verified) return true;
            if (score >= 800) return true;
            return false;
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 9. A record type, a target-typed `new()` and
    /// the `is not` pattern. C# 8 has none of them.</summary>
    internal record CoverletLanguageMarkerQuote(string Key, long Cents);

    internal static class CoverletLanguageMarker
    {
        internal static CoverletLanguageMarkerQuote Build(string key)
        {
            CoverletLanguageMarkerQuote quote = new(key, 0);
            return quote is not null ? quote : new CoverletLanguageMarkerQuote("<none>", 0);
        }
    }
}
