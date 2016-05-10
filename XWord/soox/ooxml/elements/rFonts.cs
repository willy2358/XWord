using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

namespace soox.ooxml.elements
{
    public class rFonts : OoxmlStyle
    {
        public static String TAG_NAME = "rFonts";

        public static String Default_font_for_ascii = "Calibri";
        public static String Default_font_for_ch = "宋体";

        public String hint { get; set; }
        public String ascii { get; set; }
        public String hAnsi { get; set; }
        public String eastAsia { get; set; }
        public String cs { get; set; }

        public String asciiTheme { get; set; }
        public String hAnsiTheme { get; set; }
        public String eastAsiaTheme { get; set; }
        public String csTheme { get; set; }

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void parse(System.Xml.XmlTextReader xmlReader)
        {
            validateXmlTag(xmlReader);

            ReadAttributes(xmlReader);

            this.hint = GetAttributeValue("w:hint");
            this.ascii = GetAttributeValue("w:ascii");
            this.hAnsi = GetAttributeValue("w:hAnsi");
            this.eastAsia = GetAttributeValue("w:eastAsia");
            this.cs = GetAttributeValue("w:cs");
            this.asciiTheme = GetAttributeValue("w:asciiTheme");
            this.hAnsiTheme = GetAttributeValue("w:hAnsiTheme");
            this.eastAsiaTheme = GetAttributeValue("w:eastAsiaTheme");
            this.csTheme = GetAttributeValue("w:cstheme");
        }

        public override void Decorate(OoxmlElement xmlElement)
        {
            Run r = xmlElement as Run;
            if (null == r)
            {
                return;
            }

            r.setFont(this);
        }

        
    }
}
