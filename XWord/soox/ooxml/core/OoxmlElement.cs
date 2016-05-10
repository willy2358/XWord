using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using soox.ooxml.core;


namespace soox.ooxml.core
{
    public abstract class OoxmlElement
    {
        protected List<OoxmlElement> _children = new List<OoxmlElement>();
        protected Dictionary<String, String> _attributes = new Dictionary<String, String>();
        protected String _InnerText = "";
        protected String _XmlElemName = "";
        //protected String _UnknownChildXmlText = null;
        private static Dictionary<String, Type> TagTypeRels = new Dictionary<String, Type>();
        private static HashSet<String> UnResolvedTags = new HashSet<String>();

        private static Dictionary<String, Dictionary<Object, String>> Properties = new Dictionary<String, Dictionary<Object, String>>();

        public static OoxmlElement createXmlElement(String elemName)
        {
            if (TagTypeRels.Count < 1)
            {
                InitializeTags();
            }

            if (TagTypeRels.ContainsKey(elemName))
            {
                return (OoxmlElement)Activator.CreateInstance(TagTypeRels[elemName]);
            }

            if (!UnResolvedTags.Contains(elemName))
            {
                System.Diagnostics.Debug.WriteLine("unresolved tag:" + elemName);
                UnResolvedTags.Add(elemName);
            }

            if (elemName.Trim().Length > 0)
            {
                //return new UnDefTag();
                return null;
            }
            else
            {
                return null;
            }
        }

        public abstract String getTagName();

        public virtual void parse(XmlTextReader xmlReader)
        {
            validateXmlTag(xmlReader);

            ReadAttributes(xmlReader);

            if (!isXmlNodeHasChild(xmlReader))
            {
                return;
            }

            ReadXmlNodeContent(xmlReader);

            if (this is IBlock || this is IEntity)
            {
                ApplyOoxmlStyles();
            }
        }

        public List<OoxmlElement> getChildren()
        {
            return this._children;
        }

        public String GetAttributeValue(String attrName)
        {
            if (!this._attributes.ContainsKey(attrName))
            {
                return null;
            }

            return this._attributes[attrName];
        }

        public void WriteToStream(StringBuilder stream)
        {
            stream.AppendFormat("<{0}", this._XmlElemName);
            for (int i = 0; i < this._attributes.Count; i++)
            {
                KeyValuePair<String, String> entry = this._attributes.ElementAt(i);
                stream.AppendFormat(" {0}=\"{1}\"", entry.Key, entry.Value);
            }

            if (this._InnerText.Length < 1 && this._children.Count < 1)
            {
                stream.Append("/>");
            }
            else if (this._InnerText.Length > 0)
            {
                stream.AppendFormat(">{0}</{1}>", this._InnerText, this._XmlElemName);
            }
            else
            {
                for (int j = 0; j < this._children.Count; j++)
                {
                    _children[j].WriteToStream(stream);
                }
                stream.AppendFormat("</{0}>", this._XmlElemName);
            }
        }

        protected void SetProperty(Object obj, String propName, String propValue)
        {
            if (!Properties.ContainsKey(propName))
            {
                Dictionary<Object, String> entries = new Dictionary<object, string>();
                entries.Add(obj, propValue);
                Properties.Add(propName, entries);
            }
            else
            {
                if (Properties[propName].ContainsKey(obj))
                {
                    Properties[propName][obj] = propValue;
                }
                else
                {
                    Properties[propName].Add(obj, propValue);
                }
            }
        }

        protected String GetProperty(String propName)
        {
            if (!Properties.ContainsKey(propName) || !Properties[propName].ContainsKey(this))
            {
                return "";
            }

            return Properties[propName][this];
        }

        protected void ReadXmlNodeContent(XmlTextReader xmlReader)
        {
            this._XmlElemName = xmlReader.LocalName;
            this._InnerText = xmlReader.ReadString();
            while (true)
            {
                if (xmlReader.NodeType == XmlNodeType.EndElement && xmlReader.LocalName == _XmlElemName)
                {
                    break;
                }

                OoxmlElement elem = OoxmlElement.createXmlElement(xmlReader.LocalName);
                if (null != elem)
                {
                    elem.parse(xmlReader);
                    this.addChildElement(elem);
                }

                xmlReader.Read();
            }
        }

        protected bool isXmlNodeHasChild(XmlTextReader xmlReader)
        {
            XmlNodeType type = xmlReader.MoveToContent();
            if (xmlReader.IsEmptyElement)
            {
                return false;
            }
            return true;
        }

        protected void ReadAttributes(XmlTextReader xmlReader)
        {
            for (int i = 0; i < xmlReader.AttributeCount; i++)
            {
                xmlReader.MoveToAttribute(i);
                String attr = xmlReader.Name;
                String attrVal = xmlReader.GetAttribute(attr);
                addAttribute(attr, attrVal);
            }
        }

        private void ApplyOoxmlStyles()
        {
            if (!(this is IBlock || this is IEntity))
            {
                return;
            }

            for (int i = 0; i < this._children.Count; i++)
            {
                OoxmlProperty prop = this._children[i] as OoxmlProperty;
                if (null == prop)
                {
                    continue;
                }

                prop.DecorateOwner(this);
            }
        }

        protected void addChildElement(OoxmlElement elem)
        {
            this._children.Add(elem);
        }

        protected OoxmlElement getChildElement(String elementName)
        {
            for (int i = 0; i < this._children.Count; i++)
            {
                if (this._children[i].getTagName().Equals(elementName))
                {
                    return this._children[i];
                }
            }

            return null;
        }

        protected void addAttribute(String attrName, String attrVal)
        {
            this._attributes.Add(attrName, attrVal);
        }

        protected void validateXmlTag(XmlTextReader xmlReader)
        {
            if (!xmlReader.LocalName.Equals(getTagName()))
            {
                throw new Exception("Not TAG :" + getTagName());
            }
        }

        private static void InitializeTags()
        {
            Type[] types = System.Reflection.Assembly.GetExecutingAssembly().GetTypes();
            foreach (Type t in types)
            {
                if (t.IsAbstract)
                {
                    continue;
                }

                if (t.BaseType == typeof(OoxmlElement) 
                    || t.BaseType == typeof(OoxmlProperty)
                    || t.BaseType == typeof(OoxmlStyle)
                    || t.BaseType == typeof(Block))
                {
                    System.Reflection.FieldInfo fi = t.GetField("TAG_NAME");
                    if (null != fi)
                    {
                        Object tag = fi.GetValue(Activator.CreateInstance(t));
                        String name = tag.ToString();
                        TagTypeRels.Add(name, t);
                    }
                }
            }
        }


    }
}
