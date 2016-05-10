using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.core;

namespace soox.ooxml.elements
{
    class hyperlink : Block
    {
        public static String TAG_NAME = "hyperlink";

        public List<IEntity> getEntities()
        {
            //throw new NotImplementedException();

            return null;
        }

        public override string getTagName()
        {
            return TAG_NAME;
        }

    }
}
