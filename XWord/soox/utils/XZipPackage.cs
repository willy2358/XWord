using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Zip = ICSharpCode.SharpZipLib.Zip;
using System.IO;

namespace soox.utils
{
    public class XZipPackage
    {
        private String _zipFile;
        public XZipPackage(String zipFile)
        {
            this._zipFile = zipFile;
        }

        public List<XZipEntry> getZipEntries()
        {
            List<XZipEntry> entries = new List<XZipEntry>();

            Zip.ZipInputStream s = new Zip.ZipInputStream(File.OpenRead(this._zipFile));
            Zip.ZipEntry entry = null;
            while ( null != (entry = s.GetNextEntry()))
            {
                MemoryStream ms = new MemoryStream();
                int size = 2048;
                byte[] data = new byte[2048];
                while (true)
                {
                    size = s.Read(data, 0, data.Length);
                    if (size > 0)
                    {
                        ms.Write(data, 0, size);
                    }
                    else
                    {
                        break;
                    }
                }
                ms.Position = 0;
                XZipEntry e = new XZipEntry(entry.Name);
                e.setStream(ms);
                entries.Add(e);
            }

            return entries;
        }
    }
}
