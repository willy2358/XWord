using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    /// <summary>
    /// Set of Custom Tab Stops
    /// </summary>
    class tabs : OoxmlStyle 
    {
        public static String TAG_NAME = "tabs";

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
