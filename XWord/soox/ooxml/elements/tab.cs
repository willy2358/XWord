using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    /// <summary>
    /// Custom Tab Stop
    /// </summary>
    class tab : OoxmlStyle
    {
        public static String TAG_NAME = "tab";

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
