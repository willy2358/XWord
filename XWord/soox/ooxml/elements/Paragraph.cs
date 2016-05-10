using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;
using soox.common;

namespace soox.ooxml.elements
{
    class Paragraph : Block
    {
        public static String TAG_NAME = "p";

        public pPr Property = new pPr();

        public XSize PageSize = null;

        public override void parse(System.Xml.XmlTextReader xmlReader)
        {
            base.parse(xmlReader);
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }

    }
}
