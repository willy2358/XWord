using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

using soox.common;

namespace soox.render
{
    public class Paint_Graphics : Paint
    {
        private System.Drawing.Graphics _graphics = null;
        public Paint_Graphics(System.Drawing.Graphics g)
        {
            this._graphics = g;
        }

        public void drawText(string text, float x, float y, XFont font, int color)
        {
            System.Drawing.Font f = new System.Drawing.Font(font.Name, font.CharSize);
            if (font.Bold)
            {
                f = new System.Drawing.Font(font.Name, font.CharSize, FontStyle.Bold);
            }
            Color c = Color.FromArgb(255, (color&0xff0000)>>16, (color&0xff00)>>8, color&0xff);
            System.Drawing.Brush b = new SolidBrush(c);
            this._graphics.DrawString(text, f, b, new PointF(x,y));
        }

        public XSize measureTextSize(String text, XFont font)
        {
            if (string.IsNullOrEmpty(text))
            {
                return new XSize(0.0f, 0.0f);
            }

            Font f = new Font(font.Name, font.CharSize);
            String onechar = text.Substring(0, 1);
            String twochar = onechar + onechar;
            SizeF z1 = this._graphics.MeasureString(onechar, f);
            SizeF z2 = this._graphics.MeasureString(twochar, f);

            float border = z1.Width - (z2.Width - z1.Width);

            SizeF s = this._graphics.MeasureString(text, f);
            System.Diagnostics.Debug.WriteLine("txt:{0},wid:{1},height:{2}", text, s.Width - border, s.Height);
            return new XSize(s.Width - border, s.Height);
        }

        //private System.Drawing.Font 


        public void drawRect(float x, float y, float width, float height, int color)
        {
            Color c = Color.FromArgb(255, (color&0xff0000)>>16, (color&0xff00)>>8, color&0xff);
            this._graphics.DrawRectangle(new Pen(c), x, y, width, height);
        }

        
    }
}
