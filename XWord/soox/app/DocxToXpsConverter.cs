using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XWord.soox.app
{
    public abstract class DocxToXpsConverter
    {
        public abstract bool Convert(string docx, string xps);

        public static DocxToXpsConverter CreateConverter()
        {
            return new DocxToXpsConverter_MSMQ();
        }
    }
}


