using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace soox.ooxml.core
{
    class UnDefTag : OoxmlElement
    {
        private String _TagName = "";
        //private String _InnerText = "";
        public override void parse(System.Xml.XmlTextReader xmlReader)
        {
            this._TagName = xmlReader.LocalName;
            ReadAttributes(xmlReader);

            if (!isXmlNodeHasChild(xmlReader))
            {
                return;
            }

            //this._InnerText = xmlReader.ReadString();
            //if (_InnerText.Trim().Length < 1)
            //{
                 ReadXmlNodeContent(xmlReader);
            
            //}
            //String str = xmlReader.ReadString();
            //System.Diagnostics.Debug.WriteLine(str);
        }

        public override string getTagName()
        {
            return "";
        }
    }
}
