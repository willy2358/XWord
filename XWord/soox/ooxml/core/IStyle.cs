using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.elements;

namespace soox.ooxml.core
{
    public interface IStyle
    {
        void Decorate(OoxmlElement xmlElement);
    }
}
