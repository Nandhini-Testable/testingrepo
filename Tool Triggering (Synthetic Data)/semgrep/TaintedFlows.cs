// Tool Triggering (Synthetic Data) -- Semgrep
//
// PLANTED: one method per rule in this folder's rules.yml
// EXPECTED: all 3 rules fire (orderkit-tainted-query, -command, -path); 5 findings in total, because -command matches once per Append
//
// Family net6.0, C# 10.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 10.0 (it does not compile under C# 9.0).

using System;
using System.Text;

namespace OrderKit.ToolData.Semgrep
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

    /// <summary>LANGUAGE MARKER -- C# 10. A `record struct` and an extended
    /// property pattern. C# 9 has record CLASSES only, and cannot nest a property
    /// pattern with dotted access.</summary>
    internal record struct SemgrepLanguageMarkerKey(string Tenant, string Sku);

    internal static class SemgrepLanguageMarker
    {
        internal static bool IsDomestic(SemgrepLanguageMarkerKey key)
        {
            return key is { Tenant.Length: > 0, Sku.Length: > 0 };
        }
    }
}
