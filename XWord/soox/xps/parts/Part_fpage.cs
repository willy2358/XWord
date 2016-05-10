using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Xml;

using soox.user;
using soox.opc;
using soox.xps.elements;
namespace soox.xps.parts
{
    class Part_fpage : Part_Xml
    {
        private List<Glyphs> _glyphses = null;
        public Part_fpage(string name, String zipDir) : base(name, zipDir)
        {

        }
        public override List<ooxml.core.IBlock> getContents()
        {
            return base.getContents();
        }

        public override void ApplyUpdatesToDataStream()
        {
            throw new NotImplementedException();
        }

        public List<Glyphs> GetAllGlyphs()
        {
            if (null == this._glyphses)
            {
                ParseGlyphses();
            }
            return this._glyphses;
        }

        private void ParseGlyphses()
        {
            XmlDocument doc = new XmlDocument();
            doc.Load(getDataStream());
            this._glyphses = new List<Glyphs>();
            XmlNodeList glyphs = doc.GetElementsByTagName(Glyphs.TAG_NAME);
            foreach (XmlNode xmlNode in glyphs)
            {
                Glyphs g = new Glyphs(xmlNode);
                _glyphses.Add(g);
            }
        }
    }
}
