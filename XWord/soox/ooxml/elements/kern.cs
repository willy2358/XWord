using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    /// <summary>
    /// Font Kerning  字距调整
    /// </summary>
    class kern : OoxmlStyle
    {
        public static String TAG_NAME = "w:kern";
    
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
