using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    /// <summary>
    /// (Paragraph Alignment)
    /// </summary>
    class jc :OoxmlStyle
    {
        public static String TAG_NAME = "w:jc";
        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void Decorate(OoxmlElement xmlElement)
        {
            Paragraph para = xmlElement as Paragraph;
            if (null != para)
            {
                String val = GetAttributeValue(COMMON_VALUE_ATTRIBUTE);
                if (pPr.Align_Type.center.ToString() == val)
                {
                    para.Property.AlignType = pPr.Align_Type.center;
                }
                else if (pPr.Align_Type.start.ToString() == val)
                {
                    para.Property.AlignType = pPr.Align_Type.start;
                }
                else if (pPr.Align_Type.end.ToString() == val)
                {
                    para.Property.AlignType = pPr.Align_Type.end;
                }
                else if (pPr.Align_Type.both.ToString() == val)
                {
                    para.Property.AlignType = pPr.Align_Type.both;
                }
            }
        }
    }
}
