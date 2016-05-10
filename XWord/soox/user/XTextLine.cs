using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.ooxml.elements;
using soox.render;
using soox.common;

namespace soox.user
{
    public class XTextLine
    {
        private List<Run> _runs = new List<Run>();

        private float _LineHeight = 0.0f;
        private float _YPos = 0.0f;

        public void AddRun(Run run)
        {
            this._runs.Add(run);
        }

        public void setLineHeight(float height)
        {
            _LineHeight = height;
        }

        public float getLineHeight()
        {
            return _LineHeight;
        }

        public float getYPos()
        {
            return _YPos;
        }

        public void setYPos(float pos)
        {
            this._YPos = pos;
            for (int i = 0; i < _runs.Count; i++)
            {
                Run r = _runs[i];
                r.setYPos(pos);
            }
        }

        public float getLineXPos()
        {
            return 0.0f;
        }

        public float getFontSize()
        {
            return getTextHeight();
        }

        public float getTextHeight()
        {
            float height = 0.0f;
            foreach (Run r in this._runs)
            {
                float runHei = (float)r.getFontSize();
                if (runHei > height)
                {
                    height = runHei;
                }
            }

            return height;
        }

        public String getFontName()
        {
            Run maxR = null;
            float height = -1.0f;
            foreach (Run r in this._runs)
            {
                float runHei = (float)r.getSize().Height;
                if (runHei > height)
                {
                    height = runHei;
                    maxR = r;
                }
            }

            if (null != maxR)
            {
                return maxR.getFontName();
            }
            else
            {
                return "";
            }
        }


        public float getWidth()
        {
            float width = 0.0f;
            foreach(Run r in this._runs)
            {
                width += (float)r.getSize().Width;
            }

            return width;
        }

        public void moveXPos(float offset)
        {
            foreach (Run r in this._runs)
            {
                r.moveXPos(offset);
            }
        }

        public void moveYPos(float offset)
        {
            foreach (Run r in this._runs)
            {
                r.moveYPos(offset);
            }
        }

        internal void draw(render.Paint paint)
        {
            foreach (Run r in _runs)
            {
                XFont font = new XFont();
                font.Name = r.getFontName();
                font.CharSize = r.getFontSize();
                font.Bold = r.isBold();
                paint.drawText(r.getText(), r.getPosition().X, r.getPosition().Y, font, r.getColor());
                //paint.drawRect(r.getPosition().X, r.getPosition().Y, r.getSize().Width, r.getSize().Height, r.getColor());
            }
        }
    }
}
