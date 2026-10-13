// Tool Triggering (Synthetic Data) -- Dolos
//
// PLANTED: a similarity pair with renamed identifiers and reordered statements, not a token copy
// EXPECTED: a reported similarity between SimilarSubmissionA and SimilarSubmissionB
//
// Family net5.0, C# 9.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 9.0 (it does not compile under C# 8.0).

using System;

namespace OrderKit.ToolData.Dolos
{

    /// <summary>Half of the planted similarity pair. Unlike the jscpd pair this is
    /// NOT a token-for-token copy: identifiers are renamed and independent
    /// statements are reordered. A clone detector working on exact token runs can
    /// miss it; Dolos compares structure, so it should not.</summary>
    public static class SimilarSubmissionA
    {
        public static long Settle(long[] lines, int tier)
        {
            long total = 0;
            int count = 0;
            for (int index = 0; index < lines.Length; index++)
            {
                long current = lines[index];
                if (current <= 0) continue;
                count = count + 1;
                if (tier > 2) current = current / 2;
                if (current > 100) current = current - 10;
                total = total + current;
            }
            if (count == 0) return 0;
            return total / count;
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 9. A record type, a target-typed `new()` and
    /// the `is not` pattern. C# 8 has none of them.</summary>
    internal record DolosLanguageMarkerQuote(string Key, long Cents);

    internal static class DolosLanguageMarker
    {
        internal static DolosLanguageMarkerQuote Build(string key)
        {
            DolosLanguageMarkerQuote quote = new(key, 0);
            return quote is not null ? quote : new DolosLanguageMarkerQuote("<none>", 0);
        }
    }
}
