using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    /// <summary>
    /// (Do Not Check Spelling or Grammar)
    /// </summary>
    class noProof : OoxmlStyle
    {
        public static String TAG_NAME = "w:noProof";

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
