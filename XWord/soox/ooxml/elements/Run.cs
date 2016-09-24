using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.core;

using soox.common;
using soox.serialize;

namespace soox.ooxml.elements
{
    public class Run : OoxmlElement, IEntity
    {
        public static String TAG_NAME = "w:r";
        private XSize _size = new XSize(0.0f, 0.0f);

        private rFonts _font = null;

        private XPoint _position = new XPoint(0.0f, 0.0f);
        //private static int RunIdGenerator = 0;
        private UInt32 _runId = 0;
        public Run()
        {
            //_runId = RunIdGenerator++;
        }
        public override string getTagName()
        {
            return TAG_NAME;
        }

        public String getText()
        {
            OoxmlElement tNode = getChildElement(Text.TAG_NAME);
            if (null != tNode)
            {
                return ((Text)tNode).getContent();
            }

            return null;
        }

        public void setFont(rFonts font)
        {
            this._font = font;
        }

        [JsonSerializeAttribute("Color")]
        public int Color 
        {
            get
            {
                return getColor();
            }
        }

        [JsonSerializeAttribute("RunId")]
        public UInt32 RunId
        {
            get
            {
                if (this._attributes.ContainsKey("RunId"))
                {
                    return Convert.ToUInt32(this.GetAttributeValue("RunId"));
                }
                else
                {
                    return 0;
                }
            }
            set
            {
                _runId = value;
                if (!this._attributes.ContainsKey("RunId"))
                {
                    this.addAttribute("RunId", _runId.ToString());
                }
                else
                {
                    this._attributes["RunId"] = _runId.ToString();
                }
            }
        }

        public int getColor()
        {
            String val = GetProperty(color.TAG_NAME);
            if (!string.IsNullOrEmpty(val))
            {
                return System.Convert.ToInt32(val, 16);
            }

            return color.DefaultValue;
        }

        [JsonSerializeAttribute("FontSize")]
        public float FontSize
        {
            get
            {
                return getFontSize();
            }
        }
        public float getFontSize()
        {
            String val = GetProperty(sz.TAG_NAME);
            if (!String.IsNullOrEmpty(val))
            {
                return sz.convertToPixels(System.Convert.ToInt32(val));
            }

            return sz.convertToPixels(sz.DefaultValue);
        }

        public XSize getSize()
        {
            return this._size;
        }

        public void setSize(float width, float height)
        {
            this._size.Width = width;
            this._size.Height = height;
        }

        public XPoint getPosition()
        {
            return this._position;
        }

        public void moveXPos(float offset)
        {
            this._position.X += offset;
        }

        public void moveYPos(float offset)
        {
            this._position.Y += offset;
        }

        public void setPos(float x, float y)
        {
            this._position.X = x;
            this._position.Y = y;
        }

        public void setXPos(float x)
        {
            this._position.X = x;
        }

        public void setYPos(float y)
        {
            this._position.Y = y;
        }

        public bool isBold()
        {
            String val = GetProperty(b.TAG_NAME);
            if (!String.IsNullOrEmpty(val))
            {
                return System.Convert.ToBoolean(val);
            }

            return b.DefaultValue;
        }

        public String getFontName()
        {
            if (string.IsNullOrEmpty(getText()) || null == _font)
            {
                return "";
            }

            if (IsTextAscii())
            {
                if (!string.IsNullOrEmpty(_font.ascii))
                {
                    return _font.ascii;
                }
                else
                {
                    return rFonts.Default_font_for_ascii;
                }
            }
            else
            {
                if (!string.IsNullOrEmpty(_font.eastAsia))
                {
                    return _font.eastAsia;
                }
                else
                {
                    return rFonts.Default_font_for_ch;
                }
            }
        }

        private bool IsTextAscii()
        {
            String firstChar = getText().Substring(0, 1);
            byte[] bs = System.Text.Encoding.UTF8.GetBytes(firstChar);
            if (bs.Length <= 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        internal void UpdateText(string oldPartText, string newText)
        {
            Text textElement = this.getChildElement(Text.TAG_NAME) as Text;
            if (null != textElement)
            {
                textElement.updatePartText(oldPartText, newText);
            }
        }
    }
}
