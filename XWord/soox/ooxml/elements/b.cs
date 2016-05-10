using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    public class b : OoxmlStyle    //bold
    {
        public static String TAG_NAME = "b";
        public static bool DefaultValue = false;    
        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void Decorate(OoxmlElement xmlElement)
        {
            SetProperty(xmlElement, TAG_NAME, "True");
        }
    }
}
