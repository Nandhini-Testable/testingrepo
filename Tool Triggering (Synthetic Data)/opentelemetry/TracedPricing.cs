// Tool Triggering (Synthetic Data) -- OpenTelemetry (.NET) + OTLP exporter
//
// PLANTED: span-shaped work with a counter, so an exported zero is distinguishable from a no-op processor
// EXPECTED: a non-zero span count; NOT a path-coverage number
//
// Family net6.0, C# 10.0. The core below is C# 5-compatible so it is identical on
// every family; the LanguageMarker class at the bottom is what pins this file
// to C# 10.0 (it does not compile under C# 9.0).

using System;
using System.Diagnostics;

namespace OrderKit.ToolData.Opentelemetry
{

    /// <summary>Span-shaped work for OpenTelemetry to instrument.
    ///
    /// Two things this fixture is built to avoid. First, `ActivitySource` is not in
    /// the BCL of the .NET Framework families, so the real instrumentation sits
    /// behind the same compile-time switch the corpus already uses for its planted
    /// package references -- the always-compiled path keeps the fixture analysable
    /// on all thirteen families. Second, tracing is NOT path coverage: the tool list
    /// assigns OpenTelemetry to one Path Coverage row, and a span count can never
    /// answer that metric. This folder makes the span real so the mis-assignment is
    /// demonstrable rather than asserted.
    ///
    /// The TypeScript corpus shipped an OTel bootstrap that registered a no-op
    /// processor, exported nothing and exited 0. The counter below exists so a zero
    /// here cannot be mistaken for a clean run.</summary>
    public static class TracedPricing
    {
        private static int _spanCount;

        public static int SpanCount { get { return _spanCount; } }

        public static long Price(long cents, int qty)
        {
            _spanCount = _spanCount + 1;
            long total = cents * qty;
            if (qty > 10) total = total - (total / 10);
            _spanCount = _spanCount + 1;
            return total;
        }

#if OTEL_REFS
        private static readonly ActivitySource Source =
            new ActivitySource("OrderKit.ToolData.TracedPricing");

        public static long PriceTraced(long cents, int qty)
        {
            using (Activity activity = Source.StartActivity("price"))
            {
                if (activity != null)
                {
                    activity.SetTag("orderkit.qty", qty);
                    activity.SetTag("orderkit.cents", cents);
                }
                return Price(cents, qty);
            }
        }
#endif
    }

    /// <summary>LANGUAGE MARKER -- C# 10. A `record struct` and an extended
    /// property pattern. C# 9 has record CLASSES only, and cannot nest a property
    /// pattern with dotted access.</summary>
    internal record struct OpentelemetryLanguageMarkerKey(string Tenant, string Sku);

    internal static class OpentelemetryLanguageMarker
    {
        internal static bool IsDomestic(OpentelemetryLanguageMarkerKey key)
        {
            return key is { Tenant.Length: > 0, Sku.Length: > 0 };
        }
    }
}
