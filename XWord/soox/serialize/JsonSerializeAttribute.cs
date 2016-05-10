using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace soox.serialize
{
    class JsonSerializeAttribute : Attribute
    {
        public string Key { get; set; }

        public JsonSerializeAttribute()
        {
            Key = "";
        }
        public JsonSerializeAttribute(string key)
        {
            this.Key = key;
        }


    }
}
