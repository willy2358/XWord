using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;
namespace soox.ooxml.elements
{
     class pPr : OoxmlProperty
    {
        public static String TAG_NAME = "w:pPr";
        public enum Align_Type { start = 1, end, center, both, distribute }
        public spacing Spacing = new spacing();

        public pPr()
        {
            AlignType = Align_Type.start; // default left alignmenting
        }
        
        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void DecorateOwner(OoxmlElement owner)
        {
            base.DecorateOwner(owner);
        }

        public Align_Type AlignType { set; get; }

       

    }
}
