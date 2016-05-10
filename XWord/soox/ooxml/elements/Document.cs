using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    public class Document : OoxmlElement
    {
        public static String TAG_NAME = "document";

        public override string getTagName()
        {
            return TAG_NAME;
        }
    }
}
