using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


//using System.IO.Compression;
using System.IO;
using soox.utils;
using ICSharpCode.SharpZipLib.Zip;
using ICSharpCode.SharpZipLib.Checksums;

namespace soox.opc
{
    class OPCPackage
    {
        public const String Main_Part = "word/document.xml";
        

        protected Package _package = new Package("/");  //root package

        List<XZipEntry> _zipEntries = null;
        internal OPCPackage(String package)
        {
            XZipPackage zip = new XZipPackage(package);
            _zipEntries = zip.getZipEntries();

            for (int i = 0; i < _zipEntries.Count; i++)
            {
                XZipEntry entry = _zipEntries[i];
                this._package.parseZipEntry(entry.getName(), entry);
            }
        }

        internal Part getEntryPart()
        {
            Part part = this._package.getPart(Main_Part);
            return part;
        }


        public Part GetPartByPath(string partPath)
        {
            return this._package.getPart(partPath);
        }
        internal bool saveAs(string destFile)
        {
            if (String.IsNullOrEmpty(destFile))
            {
                return false;
            }

            using (System.IO.FileStream ZipFile = System.IO.File.Create(destFile))
            {
                using (ZipOutputStream s = new ZipOutputStream(ZipFile))
                {
                    //Crc32 crc = new Crc32();

                    for (int i = 0; i < this._zipEntries.Count; i++)
                    {
                        XZipEntry xentry = this._zipEntries[i];
                        Stream stream = xentry.getStream();
                        stream.Seek(0, SeekOrigin.Begin);

                        System.Diagnostics.Debug.WriteLine("{0}:{1}", i, xentry.getName());

                        byte[] buffer = new byte[stream.Length];
                        stream.Read(buffer, 0, buffer.Length);

                        ZipEntry entry = new ZipEntry(xentry.getName());
                        entry.DateTime = DateTime.Now;
                        entry.Size = stream.Length;

                        //crc.Reset();
                        //crc.Update(buffer);
                        //entry.Crc = crc.Value;

                        s.PutNextEntry(entry);
                        s.Write(buffer, 0, buffer.Length);
                    }
                }
            }
            return true;
        }
    }
}
