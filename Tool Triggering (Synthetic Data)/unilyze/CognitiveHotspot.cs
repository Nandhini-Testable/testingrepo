// Tool Triggering (Synthetic Data) -- unilyze
//
// PLANTED: a four-level nested method, charged for depth as well as branches
// EXPECTED: a per-member cyclomaticComplexity and cognitiveComplexity peak on CognitiveHotspot.Score
//
// Family net5.0, C# 9.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 9.0 (it does not compile under C# 8.0).

using System;

namespace OrderKit.ToolData.Unilyze
{

    /// <summary>unilyze reports per-member cyclomaticComplexity AND
    /// cognitiveComplexity. Cognitive complexity charges for NESTING, not just for
    /// branch count, so this fixture nests deliberately: the inner condition sits
    /// four levels deep and each level adds its depth to the score.</summary>
    public static class CognitiveHotspot
    {
        public static int Score(int[] signals, string[] flags, bool strict)
        {
            int score = 0;
            if (signals != null)
            {
                for (int i = 0; i < signals.Length; i++)
                {
                    if (signals[i] > 0)
                    {
                        if (signals[i] > 10 && signals[i] < 100)
                        {
                            if (strict || signals[i] % 2 == 0)
                            {
                                score = score + signals[i];
                            }
                            else
                            {
                                score = score - 1;
                            }
                        }
                        else if (signals[i] >= 100)
                        {
                            foreach (string flag in flags)
                            {
                                if (flag != null && flag.Length > 2)
                                {
                                    if (flag[0] == 'x' && !strict)
                                    {
                                        score = score + 2;
                                    }
                                    else if (flag[0] == 'y')
                                    {
                                        score = score + 3;
                                    }
                                }
                            }
                        }
                    }
                    else if (signals[i] < 0)
                    {
                        score = score - signals[i];
                    }
                }
            }
            return score;
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 9. A record type, a target-typed `new()` and
    /// the `is not` pattern. C# 8 has none of them.</summary>
    internal record UnilyzeLanguageMarkerQuote(string Key, long Cents);

    internal static class UnilyzeLanguageMarker
    {
        internal static UnilyzeLanguageMarkerQuote Build(string key)
        {
            UnilyzeLanguageMarkerQuote quote = new(key, 0);
            return quote is not null ? quote : new UnilyzeLanguageMarkerQuote("<none>", 0);
        }
    }
}
