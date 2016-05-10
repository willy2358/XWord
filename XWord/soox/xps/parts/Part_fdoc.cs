using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using soox.opc;

namespace soox.xps.parts
{
    public class Part_fdoc : Part_Xml
    {
        public Part_fdoc(string name, String zipDir) : base(name, zipDir)
        {

        }

        public override List<ooxml.core.IBlock> getContents()
        {
            return base.getContents();
        }

        public override void ApplyUpdatesToDataStream()
        {
        }
    }
}
