using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    /// <summary>
    /// Referenced Paragraph Style
    /// </summary>
    class pStyle : OoxmlStyle
    {
        public static String TAG_NAME = "w:pStyle";

        public override void Decorate(OoxmlElement xmlElement)
        {
            //throw new NotImplementedException();
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }
    }
}
