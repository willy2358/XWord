using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class sz : OoxmlStyle
    {
        public static String TAG_NAME = "w:sz";
        public static int DefaultValue = 21;    //五号 ooxml unit: half-point = 1/144 of an inch

        public static float PER_POINTS = 0.5f;       //点单位

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void Decorate(OoxmlElement xmlElement)
        {
            String val = GetAttributeValue(COMMON_VALUE_ATTRIBUTE);
            if (null != val)
            {
                SetProperty(xmlElement, TAG_NAME, val);
            }
        }

        public static float convertToPixels(int val)
        {
            return val * PER_POINTS;
        }
    }
}
