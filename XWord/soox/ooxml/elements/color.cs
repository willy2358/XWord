using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class color : OoxmlStyle
    {
        public static String TAG_NAME = "w:color";
        public static int DefaultValue = 0x000000;  //RGB, black

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void Decorate(OoxmlElement element)
        {
            String val = GetAttributeValue(COMMON_VALUE_ATTRIBUTE);
            if (null != val)
            {
                SetProperty(element, TAG_NAME, val);
            }
        }
    }
}
