// Tool Triggering (Synthetic Data) -- Roslyn SAST analyzers
//
// PLANTED: five sinks reachable from public parameters, all on BCL types that need no restore
// EXPECTED: diagnostics in the CA2100 / SCS0002, SCS0001, CA3003, CA5351 and CA5394 families
//
// Family net5.0, C# 9.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 9.0 (it does not compile under C# 8.0).

using System;
using System.Diagnostics;
using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace OrderKit.ToolData.RoslynSast
{

    /// <summary>Five sinks, every one reachable from a public parameter. All five
    /// use types in the BCL of every family in this corpus, so nothing here needs a
    /// restore to be ANALYSED -- which matters, because an analyzer that cannot
    /// bind its types reports nothing and exits clean.
    ///
    /// A library does not control its callers, so every public parameter is an
    /// untrusted source. An engine that only recognises argv, an environment
    /// variable or a request object will miss all five of these.</summary>
    public static class InjectionSinks
    {
        /// <summary>SQL assembled by concatenation. CA2100 / SCS0002.</summary>
        public static string BuildOrderQuery(string sku)
        {
            return "SELECT * FROM orders WHERE sku = '" + sku + "'";
        }

        /// <summary>Command line assembled from a parameter. SCS0001.</summary>
        public static Process RunReport(string reportName)
        {
            ProcessStartInfo info = new ProcessStartInfo();
            info.FileName = "cmd.exe";
            info.Arguments = "/c report.bat " + reportName;
            return Process.Start(info);
        }

        /// <summary>Path built from a parameter with no containment check. CA3003.</summary>
        public static string ReadTemplate(string root, string templateName)
        {
            string path = Path.Combine(root, templateName);
            if (!File.Exists(path)) return string.Empty;
            return File.ReadAllText(path);
        }

        /// <summary>MD5 for an integrity check. CA5351.</summary>
        public static string Fingerprint(string payload)
        {
            MD5 md5 = MD5.Create();
            byte[] hash = md5.ComputeHash(Encoding.UTF8.GetBytes(payload));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.Length; i++) sb.Append(hash[i].ToString("x2"));
            return sb.ToString();
        }

        /// <summary>System.Random for a security token. CA5394.</summary>
        public static string IssueToken(int length)
        {
            Random rng = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) sb.Append((char)('a' + rng.Next(0, 26)));
            return sb.ToString();
        }
    }

    /// <summary>LANGUAGE MARKER -- C# 9. A record type, a target-typed `new()` and
    /// the `is not` pattern. C# 8 has none of them.</summary>
    internal record RoslynSastLanguageMarkerQuote(string Key, long Cents);

    internal static class RoslynSastLanguageMarker
    {
        internal static RoslynSastLanguageMarkerQuote Build(string key)
        {
            RoslynSastLanguageMarkerQuote quote = new(key, 0);
            return quote is not null ? quote : new RoslynSastLanguageMarkerQuote("<none>", 0);
        }
    }
}
