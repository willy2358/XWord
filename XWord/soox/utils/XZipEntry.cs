using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace soox.utils
{
    public class XZipEntry
    {
        private String _name;
        private Stream _stream;
        public XZipEntry(String name)
        {
            this._name = name;
        }

        public String getName()
        {
            return this._name;
        }

        public String getZipDir()
        {
            int lPos = this._name.LastIndexOf('/');
            if (lPos < 0)
            {
                return "/";
            }
            else
            {
                return this._name.Substring(0, lPos + 1);
            }
        }

        public Stream getStream()
        {
            return this._stream;
        }

        public void setStream(Stream stream)
        {
            this._stream = stream;
        }
    }
}
