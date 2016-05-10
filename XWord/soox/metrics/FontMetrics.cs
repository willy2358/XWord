using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.common;


namespace soox.metrics
{
    class FontMetrics
    {
        private const String FONT_NAME_CH_SONGTI = "宋体";
        static private List<FontMetricItem> Metrics = new List<FontMetricItem>();
        static FontMetrics()
        {
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 10.5f, 1.0f, 10.56f, 15.6f)); //五， 单倍行距
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 10.5f, 1.5f, 10.56f, 23.4f));
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 10.5f, 2.0f, 10.56f, 31.2f));
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 10.5f, 12.0f, 1.0f, 10.56f, 15.96f)); //5 - s4, 1倍行距
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 10.5f, 16.0f, 1.0f, 10.56f, 25.20f));  //5 - 3, 1 
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 10.5f, 15.0f, 1.0f, 10.56f, 24.84f));  //5 - s3, 1 

            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 12.0f, 1.0f, 12.0f, 15.6f)); //小四
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 12.0f, 1.5f, 12.0f, 23.4f)); 
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 12.0f, 10.5f, 1, 12.0f, 15.24f)); //s4 - 5, 1倍行距

            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 16.0f, 1.0f, 15.96f, 31.20f)); //三号
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 16.0f, 10.5f, 1.0f, 15.96f, 21.60f)); //3 - 5, 1

            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 15.0f, 1.0f, 15.0f, 31.20f)); //s3
            Metrics.Add(new FontMetricItem(FONT_NAME_CH_SONGTI, 15.0f, 10.5f, 1.0f, 15.0f, 21.96f)); //s3 - 5, 1



        }

        public static XSize GetFontMetrics(String fontName, float fontSize, float lineSpacing)
        {
            for (int i = 0; i < Metrics.Count; i++)
            {
                FontMetricItem m = Metrics[i];
                if (m.FontName == fontName
                    && m.ThisLineFontSize == fontSize
                    && m.Spacing == lineSpacing
                    && m.NextLineFontSize == fontSize)
                {
                    return m.Metric;
                }
            }

            return null;
        }

        public static XSize GetFontMetrics(String fontName, float lineFontSize, float nextLineFontSize, float lineSpacing)
        {
            for (int i = 0; i < Metrics.Count; i++)
            {
                FontMetricItem m = Metrics[i];
                if (m.FontName == fontName
                    && m.ThisLineFontSize == lineFontSize
                    && m.Spacing == lineSpacing
                    && m.NextLineFontSize == nextLineFontSize)
                {
                    return m.Metric;
                }
            }

            return null;
        }
    }
}
