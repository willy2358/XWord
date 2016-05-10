using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using soox.ooxml.elements;
using soox.common;

using soox.serialize;
namespace soox.user
{
    public class XLineBlock : ISerializable
    {
        public XLineBlock(Run myRun, XPoint position, String text)
        {
            this.MyRun = myRun;
            this.Position = position;
            this.Text = text;
        }
        public Run MyRun { private set; get; }

        [JsonSerializeAttribute("Position")]
        public XPoint Position { private set; get; }

        [JsonSerializeAttribute("Text")]
        public String Text { get; private set; }
    }
}
