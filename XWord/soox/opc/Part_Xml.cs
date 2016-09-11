using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.user;
using soox.ooxml.core;

using System.Xml;
using System.IO;
using soox.ooxml.elements;

namespace soox.opc
{
    public class Part_Xml : Part
    {
        private OoxmlElement _root = null;

        public Part_Xml(String name, string zipDir) : base(name, zipDir)
        {
        }

        public override List<IBlock> getContents()
        {
            if (null == this._root)
            {
                parseXmlStream();
            }

            OoxmlElement scopeNode = getScopeChildNode();
            if (null != scopeNode)
            {
                return getBlockNodes(scopeNode);
            }
            else
            {
                return getBlockNodes(this._root);
            }
        }

        private OoxmlElement getScopeChildNode()
        {
            List<OoxmlElement> nodes = this._root.getChildren();
            if (nodes.Count < 1)
            {
                return null;
            }

            Type t = nodes[0].GetType();
            if (null != t.GetInterface(typeof(IScope).Name))
            {
                return nodes[0];
            }

            return null;
        }

        public void parseXmlStream()
        {
            try
            {
                XmlTextReader xml = new XmlTextReader(this.getDataStream());
                while (xml.Read())
                {
                    XmlNodeType type = xml.NodeType;
                    if (type == XmlNodeType.Element)
                    {
                        this._root = OoxmlElement.createXmlElement(xml.Name);
                        this._root.parse(xml);
                    }
                }
            }
            catch (Exception ex)
            {
                String error = ex.Message;
            }
        }

        private List<IBlock> getBlockNodes(OoxmlElement parent)
        {
            if (null == parent)
            {
                return null;
            }

            List<IBlock> blocks = new List<IBlock>();
            foreach (OoxmlElement elem in parent.getChildren())
            {
                if (null != elem.GetType().GetInterface(typeof(IBlock).Name))
                {
                    blocks.Add(elem as IBlock);
                }
            }

            return blocks;
        }

        public override void ApplyUpdatesToDataStream()
        {
            StringBuilder sbStream = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n", 5000);

            this._root.WriteToStream(sbStream);

            this._DataStream.Seek(0, SeekOrigin.Begin);

            this._DataStream.Flush();

            byte[] data = System.Text.Encoding.UTF8.GetBytes(sbStream.ToString());

            this._DataStream.Write(data, 0, data.Length);
        }
    }
}
