using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using soox.ooxml.core;
using soox.ooxml.elements;
using System.Xml;
using System.Drawing;

namespace soox.xps.elements
{
    class Glyphs : OoxmlElement, IEntity
    {
        public static String TAG_NAME = "Glyphs";
        public static String ATTR_Name = "Name";
        public static String ATTR_BidiLevel = "BidiLevel";
        public static String ATTR_Fill = "Fill";
        public static String ATTR_FontUri = "FontUri";
        public static String ATTR_FontRenderingEmSize = "FontRenderingEmSize";
        public static String ATTR_StyleSimulations = "StyleSimulations";
        public static String ATTR_OriginX = "OriginX";
        public static String ATTR_OriginY = "OriginY";
        public static String ATTR_UnicodeString = "UnicodeString";
        public static String ATTR_Indices = "Indices";

        private XmlNode _xmlNode;
        private string _unicodeString = "";
        private float _originX;
        private float _originY;
        private Color _fillColor;
        private float _fontSize;

        public Glyphs()
        {

        }
        public Glyphs(XmlNode xmlNode)
        {
            this._xmlNode = xmlNode;
            ParseFromXmlNode(xmlNode);
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public String getText()
        {
            return this._unicodeString;
        }


        public common.XPoint getPosition()
        {
            //return new common.XPoint()
            return new common.XPoint(_originX, _originY);
        }

        public common.XSize getSize()
        {
            return new common.XSize(this._unicodeString.Length * this._fontSize, this._fontSize);
        }

        public int getColor()
        {
            return this._fillColor.ToArgb();
        }

        public string getFontName()
        {
            throw new NotImplementedException();
        }

        public float getFontSize()
        {
            return this._fontSize;
        }

        private void ParseFromXmlNode(XmlNode node)
        {
            XmlAttribute attrStr = node.Attributes.GetNamedItem(ATTR_UnicodeString) as XmlAttribute;
            if (null != attrStr)
            {
                this._unicodeString = attrStr.Value;
            }
            XmlAttribute attrX = node.Attributes.GetNamedItem(ATTR_OriginX) as XmlAttribute;
            if (null != attrX)
            {
                this._originX = (float)Convert.ToDouble(attrX.Value);
            }
            XmlAttribute attrY = node.Attributes.GetNamedItem(ATTR_OriginY) as XmlAttribute;
            if (null != attrY)
            {
                this._originY = (float)Convert.ToDouble(attrY.Value);
            }
            XmlAttribute attrFill = node.Attributes.GetNamedItem(ATTR_Fill) as XmlAttribute;
            if (null != attrFill)
            {
                this._fillColor = (System.Drawing.Color)(new System.Drawing.ColorConverter().ConvertFromString(attrFill.Value));// Convert.ToInt32(attrFill.Value.TrimStart('#'));
            }
            XmlAttribute attrEm = node.Attributes.GetNamedItem(ATTR_FontRenderingEmSize) as XmlAttribute;
            if (null != attrEm)
            {
                this._fontSize = Convert.ToSingle(attrEm.Value);
            }
        }
    }
}


