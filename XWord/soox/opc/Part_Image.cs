using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;
namespace soox.opc
{
    public class Part_Image : Part
    {
        public Part_Image(String name, String zipDir) : base(name, zipDir)
        {

        }

        public override List<IBlock> getContents()
        {
            return null;
        }


        public override void ApplyUpdatesToDataStream()
        {
            throw new NotImplementedException();
        }
    }
}
