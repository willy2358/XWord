using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class spacing : OoxmlStyle
    {
        public enum enumLineRule { auto = 1, exactly }

        public enumLineRule LineRule = enumLineRule.auto;
        public float LineSpacing = 1.0f;

        public static String TAG_NAME = "w:spacing";

        public static float PER_POINTS = 0.05f;       //点单位, 

        public override void Decorate(OoxmlElement xmlElement)
        {
            Paragraph p = xmlElement as Paragraph;
            if (null == p)
            {
                return;
            }
            string rule = GetAttributeValue("lineRule");
            if (rule == enumLineRule.auto.ToString())
            {
                LineRule = enumLineRule.auto;
            }
            else if (rule == enumLineRule.exactly.ToString())
            {
                LineRule = enumLineRule.exactly;
            }

            string line = GetAttributeValue("line");
            if (!string.IsNullOrEmpty(line))
            {
                LineSpacing = (float)Convert.ToDouble(line) / 240.0f;
            }

            p.Property.Spacing = this;
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }

          
    }
}
