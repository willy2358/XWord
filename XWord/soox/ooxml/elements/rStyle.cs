using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class rStyle : OoxmlStyle
    {
        public static String TAG_NAME = "rStyle";

        public override void Decorate(OoxmlElement xmlElement)
        {
            
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }
    }
}
