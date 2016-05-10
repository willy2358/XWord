using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.common;
namespace soox.metrics
{
    class FontMetricItem
    {
        public String FontName;
        public float ThisLineFontSize;
        public float Spacing;
        public XSize Metric;
        public float EmSize;
        public float LineSpanDis;
        public float NextLineFontSize;

        public FontMetricItem(String fontName, float size, float spacing, float emSize, float lineSpanDis)
        {
            FontName = fontName;
            ThisLineFontSize = size;
            NextLineFontSize = size;
            Spacing = spacing;
            EmSize = emSize;
            LineSpanDis = lineSpanDis;
            Metric = new XSize(emSize, lineSpanDis);
        }

        public FontMetricItem(String fontName, float thisLineFontSize, float nextLineFontSize, float spacing, float emSize, float lineSpanDis)
        {
            FontName = fontName;
            ThisLineFontSize = thisLineFontSize;
            NextLineFontSize = nextLineFontSize;
            Spacing = spacing;
            EmSize = emSize;
            LineSpanDis = lineSpanDis;
            Metric = new XSize(emSize, lineSpanDis);
        }
    }
}
