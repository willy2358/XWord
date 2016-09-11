using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class tr : OoxmlElement
    {
        public static String TAG_NAME = "w:tr";

        public override string getTagName()
        {
            return TAG_NAME;
        }
    }
}
