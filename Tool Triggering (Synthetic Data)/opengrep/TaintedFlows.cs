// Tool Triggering (Synthetic Data) -- Opengrep
//
// PLANTED: the same three planted flows, with this folder's own copy of the rules
// EXPECTED: all 3 rules fire, matching the semgrep folder
//
// Family net5.0, C# 9.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 9.0 (it does not compile under C# 8.0).

using System;
using System.Text;

namespace OrderKit.ToolData.Opengrep
{

    /// <summary>One method per rule in this folder's rules.yml, written to the exact
    /// shape each pattern matches:
    ///
    ///   orderkit-tainted-query    string.Format("SELECT ...", ...)
    ///   orderkit-tainted-command  an Append inside a public static BuildCommand
    ///   orderkit-tainted-path     root.TrimEnd('/') + "/" + name
    ///
    /// The rules are matched against THIS file, so a zero result here means the
    /// scan did not run or the C# grammar did not load -- not that the code is
    /// clean. That distinction is the whole point of the fixture.</summary>
    public static class TaintedFlows
    {
        public static string BuildQuery(string sku, string tenant)
        {
            return string.Format("SELECT * FROM orders WHERE sku = '{0}' AND tenant = '{1}'",
                                 sku, tenant);
        }

        public static string BuildCommand(string verb, string[] args)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(verb);
            for (int i = 0; i < args.Length; i++)
            {
                sb.Append(" ");
                sb.Append(args[i]);
            }
            return sb.ToString();
        }

        public static string BuildReportPath(string root, string name)
        {
            return root.TrimEnd('/') + "/" + name;
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 9. A record type, a target-typed `new()` and
    /// the `is not` pattern. C# 8 has none of them.</summary>
    internal record OpengrepLanguageMarkerQuote(string Key, long Cents);

    internal static class OpengrepLanguageMarker
    {
        internal static OpengrepLanguageMarkerQuote Build(string key)
        {
            OpengrepLanguageMarkerQuote quote = new(key, 0);
            return quote is not null ? quote : new OpengrepLanguageMarkerQuote("<none>", 0);
        }
    }
}
