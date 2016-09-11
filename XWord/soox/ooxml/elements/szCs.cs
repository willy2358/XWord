using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class szCs : OoxmlStyle
    {
        public static String TAG_NAME = "w:szCs";

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void Decorate(OoxmlElement xmlElement)
        {
            return;
        }
    }
}
