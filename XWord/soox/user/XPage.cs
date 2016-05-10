using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace soox.user
{
    public class XPage
    {
        private List<XTextLine> _lines = new List<XTextLine>();
        public void draw(render.Paint paint)
        {
            foreach (XTextLine line in _lines)
            {
                line.draw(paint);
            }
        }

        internal void addTextLines(List<XTextLine> lines)
        {
            this._lines.AddRange(lines);
        }

        internal void addTextLine(XTextLine line)
        {
            this._lines.Add(line);
        }
    }
}
