using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;
using soox.common;

namespace soox.ooxml.elements
{
    class Table : Block
    {
        public static String TAG_NAME = "w:tbl";

        public new List<IEntity> getEntities()
        {
            return null;
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }

        public XSize getSize()
        {
            throw new NotImplementedException();
        }
    }
}
