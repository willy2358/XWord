using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.render;
using soox.common;

namespace soox.ooxml.core
{
    public interface IEntity
    {
        String getText();
        XPoint getPosition();
        XSize getSize();

        int getColor();

        string getFontName();

        float getFontSize();
    }
}
