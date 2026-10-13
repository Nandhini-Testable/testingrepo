// Tool Triggering (Synthetic Data) -- Lizard
//
// PLANTED: one method with 20 decision points
// EXPECTED: max_ccn >= 15 on ComplexityHotspot.Rate
//
// Family net5.0, C# 9.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 9.0 (it does not compile under C# 8.0).

using System;

namespace OrderKit.ToolData.Lizard
{

    /// <summary>A single method with 20 decision points, so lizard's CCN for it is
    /// 21. dataset.json asserts lizard.max_ccn >= 15; this is the fixture that
    /// makes the assertion true from this folder alone.</summary>
    public static class ComplexityHotspot
    {
        public static long Rate(long cents, int qty, string region, string tier,
                                bool expedited, bool taxable)
        {
            long total = cents;
            if (qty <= 0) return 0;
            if (qty > 1 && qty < 10) total = total * qty;
            else if (qty >= 10 && qty < 100) total = total * qty - 50;
            else if (qty >= 100) total = total * qty - 500;

            if (region == "eu") total = total + 120;
            else if (region == "apac") total = total + 220;
            else if (region == "latam") total = total + 320;
            else if (region == "mea") total = total + 420;

            if (tier == "gold" || tier == "platinum") total = total - 100;
            else if (tier == "silver") total = total - 50;

            if (expedited && qty > 5) total = total + 900;
            if (taxable && region != "eu") total = total + total / 10;

            for (int i = 0; i < qty; i++)
            {
                if (i % 7 == 0 && i != 0) total = total - 1;
                while (total < 0) total = total + 10;
            }

            switch (region)
            {
                case "eu": total = total + 1; break;
                case "apac": total = total + 2; break;
                default: total = total + 3; break;
            }

            return total > 0 ? total : 0;
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 9. A record type, a target-typed `new()` and
    /// the `is not` pattern. C# 8 has none of them.</summary>
    internal record LizardLanguageMarkerQuote(string Key, long Cents);

    internal static class LizardLanguageMarker
    {
        internal static LizardLanguageMarkerQuote Build(string key)
        {
            LizardLanguageMarkerQuote quote = new(key, 0);
            return quote is not null ? quote : new LizardLanguageMarkerQuote("<none>", 0);
        }
    }
}
