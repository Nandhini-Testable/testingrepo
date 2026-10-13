// Tool Triggering (Synthetic Data) -- jscpd
//
// PLANTED: a duplicate pair identical modulo one name, well past --min-tokens 50
// EXPECTED: duplicates >= 1, and sources > 0
//
// Family net6.0, C# 10.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 10.0 (it does not compile under C# 9.0).

using System;

namespace OrderKit.ToolData.Jscpd
{

    /// <summary>Half of the planted duplicate pair. The two blocks are identical
    /// modulo the class name and one local, and run to well over jscpd's
    /// --min-tokens 50, so a correct run reports at least one clone. dataset.json
    /// asserts jscpd.duplicates >= 1.</summary>
    public static class DuplicateBlockB
    {
        public static long Settle(long[] lines, string region, int tier)
        {
            long runningTotal = 0;
            for (int i = 0; i < lines.Length; i++)
            {
                long amount = lines[i];
                if (amount <= 0) continue;
                if (tier == 1) amount = amount - (amount / 20);
                else if (tier == 2) amount = amount - (amount / 10);
                else if (tier == 3) amount = amount - (amount / 5);
                if (region == "eu") amount = amount + 120;
                else if (region == "apac") amount = amount + 220;
                else amount = amount + 20;
                if (amount < 0) amount = 0;
                runningTotal = runningTotal + amount;
            }
            if (runningTotal > 1000000) runningTotal = 1000000;
            return runningTotal;
        }
    }
}
