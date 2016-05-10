using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using soox.ooxml.core;
using System.Xml;

namespace soox.xps.elements
{
    class FixedDocument : Block, IScope
    {
        public static String TAG_NAME = "FixedDocument";

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
