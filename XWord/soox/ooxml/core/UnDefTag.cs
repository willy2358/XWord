﻿using System;
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
            this._TagName = xmlReader.Name;
            this._XmlElemName = this._TagName;
            ReadAttributes(xmlReader);

            if (!isXmlNodeHasChild(xmlReader))
            {
                return;
            }

            ReadXmlNodeContent(xmlReader);
        }

        public override void parse(System.Xml.XmlNode xmlNode)
        {
            this._TagName = xmlNode.Name;
            this._XmlElemName = this._TagName;
            ReadAttributes(xmlNode);

            if (!isXmlNodeHasChild(xmlNode))
            {
                return;
            }

            ReadXmlNodeContent(xmlNode);
        }
        public override string getTagName()
        {
            return "";
        }
    }
}
