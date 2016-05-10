using soox.serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace soox.common
{
    public class XPoint
    {
        [JsonSerializeAttribute("X")]
        public float X{get; set;}
        [JsonSerializeAttribute("Y")]
        public float Y { get; set; }

        public XPoint(float x, float y)
        {
            this.X = x;
            this.Y = y;
        }
    }
}
