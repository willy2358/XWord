using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.common;
namespace soox.render
{
    public interface Paint
    {
        void drawText(String text, float x, float y, XFont font, int color);
        void drawRect(float x, float y, float width, float height, int color);

        XSize measureTextSize(String text, XFont font);


    }
}
