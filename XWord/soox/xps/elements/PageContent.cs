using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using soox.ooxml.core;

namespace soox.xps.elements
{
    class PageContent : Block
    {
        public static String TAG_NAME = "PageContent";
        public static string PAGE_SOURCE_PART = "Source";
        public override string getTagName()
        {
            return TAG_NAME;
        }

        public string GetMyPagePartPath()
        {
            if (_attributes.Keys.Contains(PAGE_SOURCE_PART))
            {
                return _attributes[PAGE_SOURCE_PART];
            }

            return "";
        }
    }
}
