using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace  soox.opc
{
    class OPCPackage_XPS : OPCPackage
    {
        private const string FIXED_DOC_PART = "Documents/1/FixedDoc.fdoc";
        public OPCPackage_XPS(string package):base(package)
        {

        }

        public Part GetFixedDocPart()
        {
            return this._package.getPart(FIXED_DOC_PART);
        }
    }
}
