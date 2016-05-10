using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace soox.common
{
    public class XFont
    {
        public XFont()
        {

        }



        public XFont(String name, float charSize)
        {
            Name = name;
            CharSize = charSize;
        }

        public String Name
        {
            set;
            get;
        }

        public bool Bold
        {
            set;
            get;
        }
        public float CharSize
        {
            set;
            get;
        }
    }
}
