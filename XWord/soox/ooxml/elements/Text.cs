using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class Text : OoxmlElement
    {
        public static String TAG_NAME = "w:t";

        private String _Content = "";

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public override void parse(System.Xml.XmlTextReader xmlReader)
        {
            validateXmlTag(xmlReader);

            _Content = xmlReader.ReadString();
        }

        public override void parse(System.Xml.XmlNode xmlNode)
        {
            validateXmlTag(xmlNode);

            _Content = xmlNode.InnerText;
            this._InnerText = _Content;
        }

        public String getContent()
        {
            return this._Content;
        }

        public string updatePartText(string oldPartText, string newText)
        {
            this._Content = this._Content.Replace(oldPartText, newText);
            return this._Content;
        }
    }
}
