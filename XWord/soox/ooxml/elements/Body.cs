using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    public class Body : OoxmlElement, IScope
    {
        public static String TAG_NAME = "w:body";

        public override void parse(XmlTextReader xmlReader)
        {
            base.parse(xmlReader);
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }
    }
}
