using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;
namespace soox.ooxml.elements
{
    class rPr : OoxmlProperty
    {
        public static String TAG_NAME = "w:rPr";

        public override string getTagName()
        {
            return TAG_NAME;
        }

        //public void Decorate(OoxmlElement xmlElement)
        //{
        //    return;
        //}

        public override void DecorateOwner(OoxmlElement owner)
        {
            base.DecorateOwner(owner);
        }
    }
}
