using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.user;
using soox.ooxml.core;
using System.IO;
using soox.xps.parts;

namespace soox.opc
{
    public abstract class  Part
    {
        public abstract List<IBlock> getContents();
        protected String _Name;
        protected String _zipDir;
        protected Stream _DataStream;

        public abstract void ApplyUpdatesToDataStream();

        public Part(String name, String zipDir)
        {
            this._Name = name;
            this._zipDir = zipDir;
        }

        public String getName()
        {
            return this._Name;
        }

        public String getZipDir()
        {
            return this._zipDir;
        }

        public void setDataStream(Stream stream)
        {
            this._DataStream = stream;
        }

        public Stream getDataStream()
        {
            return this._DataStream;
        }

        public static Part createPart(String partName, string zipDir)
        {
            if (partName.EndsWith(".xml"))
            {
                return new Part_Xml(partName, zipDir);
            }
            else if (partName.EndsWith(".png"))
            {
                return new Part_Image(partName, zipDir);
            }
            else if (partName.EndsWith(".fdoc"))
            {
                return new Part_fdoc(partName, zipDir);
            }
            else if (partName.EndsWith(".fpage"))
            {
                return new Part_fpage(partName, zipDir);
            }

            return null;
        }
    }
}
