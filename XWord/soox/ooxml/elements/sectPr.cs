using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class sectPr : OoxmlElement
    {
        public static String TAG_NAME = "sectPr";

        //页的尺寸以像素点的1/20为单位
        public static float PER_POINTS = 0.05f;

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public void Decorate(OoxmlElement xmlElement)
        {
            return;
        }

        public float convertToPixels(int val)
        {
            return val * PER_POINTS;
        }
    }
}
