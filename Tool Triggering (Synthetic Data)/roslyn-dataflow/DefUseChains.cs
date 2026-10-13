// Tool Triggering (Synthetic Data) -- Roslyn SemanticModel.AnalyzeDataFlow
//
// PLANTED: a dead store, a cross-branch DU pair, a captured write and a written-never-read local
// EXPECTED: non-trivial AlwaysAssigned / DataFlowsOut / Captured sets per region
//
// Family net6.0, C# 10.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 10.0 (it does not compile under C# 9.0).

using System;

namespace OrderKit.ToolData.RoslynDataflow
{

    /// <summary>Def-use fixtures. AnalyzeDataFlow yields AlwaysAssigned,
    /// DataFlowsIn, DataFlowsOut, ReadInside, WrittenInside and Captured for a
    /// region; each method below makes one of those sets non-trivially wrong if an
    /// engine computes it by counting assignments instead of by following flow.
    ///
    /// The corpus's roster assigns All-Definitions and All-Uses coverage to
    /// Stryker.NET, a mutation tester that computes no def-use chains at all. This
    /// folder is the fixture for the API that actually does.</summary>
    public static class DefUseChains
    {
        /// <summary>DEAD STORE: the first definition of `total` is never read --
        /// it is overwritten on every path before any use.</summary>
        public static long DeadStore(long cents, bool apply)
        {
            long total = 999;
            if (apply) total = cents * 2;
            else total = cents;
            return total;
        }

        /// <summary>DU-PAIR ACROSS A BRANCH: `scale` is defined on one path and
        /// used on a later, different path. A per-block analysis misses the pair.</summary>
        public static long CrossBranchUse(long cents, int qty)
        {
            long scale;
            if (qty > 10) scale = 2;
            else scale = 1;
            long staged = cents * scale;
            return staged - scale;
        }

        /// <summary>CAPTURED: `budget` is written by the closure, so it flows out of
        /// the lambda's region even though the enclosing method never assigns it
        /// again.</summary>
        public static long CapturedWrite(long[] amounts)
        {
            long budget = 0;
            Action<long> accumulate = delegate(long amount) { budget = budget + amount; };
            for (int i = 0; i < amounts.Length; i++) accumulate(amounts[i]);
            return budget;
        }

        /// <summary>WRITTEN NEVER READ: `audit` is assigned in every iteration and
        /// read nowhere, which is a real finding rather than a style note.</summary>
        public static int WrittenNeverRead(int[] values)
        {
            int kept = 0;
            for (int i = 0; i < values.Length; i++)
            {
                int audit = values[i] * 3;
                if (values[i] > 0) kept = kept + 1;
            }
            return kept;
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 10. A `record struct` and an extended
    /// property pattern. C# 9 has record CLASSES only, and cannot nest a property
    /// pattern with dotted access.</summary>
    internal record struct RoslynDataflowLanguageMarkerKey(string Tenant, string Sku);

    internal static class RoslynDataflowLanguageMarker
    {
        internal static bool IsDomestic(RoslynDataflowLanguageMarkerKey key)
        {
            return key is { Tenant.Length: > 0, Sku.Length: > 0 };
        }
    }
}
