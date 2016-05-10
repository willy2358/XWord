using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace soox.ooxml.core
{
    public abstract class OoxmlStyle : OoxmlElement, IStyle
    {
        public const String COMMON_VALUE_ATTRIBUTE = "w:val";

        public abstract void Decorate(OoxmlElement xmlElement);
    }
}
