using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using soox.serialize;

namespace soox.user
{
    public class XPage2 : ISerializable
    {
        //private List<XLineBlock> _lineBlocks = new List<XLineBlock>();
        private XLineBlockCollection _lineBlocks = new XLineBlockCollection();

        [JsonSerializeAttribute("pageNumber")]
        public int PageNum { get; private set; }
        public XPage2(int pageNum)
        {
            this.PageNum = pageNum;
        }

        [JsonSerializeAttribute("AddLineBlock")]
        public void AddLineBlock(XLineBlock block)
        {
            this._lineBlocks.Add(block);
        }

        [JsonSerializeAttribute("lineBlocks")]
        public XLineBlockCollection LineBlocks
        {
            get
            {
                return _lineBlocks;
            }
        }

        public bool UpdateRunText(int runId, string oldText, string newText)
        {
            foreach(XLineBlock line in _lineBlocks)
            {
                if (line.MyRun.RunId == runId)
                {
                    line.MyRun.UpdateText(oldText, newText);
                    return true;
                }
            }
            return false;
        }
    }
}
