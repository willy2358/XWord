using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.IO;
namespace soox.opc
{
    class Package
    {
        private List<Package> _packages = new List<Package>();
        private List<Part> _parts = new List<Part>();

        private String _Name;


        public Package(String package)
        {
            this._Name = package;
        }

        public String getName()
        {
            return this._Name;
        }


        public void parseZipEntry(String path, soox.utils.XZipEntry entry)
        {
            if (IsRelationshipPart(path))
            {
                parseRelationships(entry);
                return;
            }

            String[] ps = path.Split('/');
            if (ps.Length == 1)
            {
                Part part = Part.createPart(path, entry.getZipDir());
                if (null != part)
                {
                    part.setDataStream(entry.getStream());
                    this._parts.Add(part);
                }
            }
            else if (ps.Length > 1)
            {
                Package package = this.getPackage(ps[0]);
                package.parseZipEntry(path.Substring(ps[0].Length + 1), entry);
            }
        }

        private void parseRelationships(utils.XZipEntry entry)
        {
            
        }

        private bool IsRelationshipPart(string name)
        {
            return name.StartsWith("_rels");
        }

        private Package getPackage(string packName)
        {
            foreach (Package p in this._packages)
            {
                if (p.getName().Equals(packName))
                {
                    return p;
                }
            }

            Package newPack = new Package(packName);
            this._packages.Add(newPack);
            return newPack;
        }

        public Part getPart(String name)
        {
            String[] ps = name.Split('/');
            if (ps.Length > 1)
            {
                Package pack = getPackage(ps[0]);
                if (null != pack)
                {
                    return pack.getPart(name.Substring(ps[0].Length + 1));
                }
            }
            else if (ps.Length == 1)
            {
                foreach (Part p in this._parts)
                {
                    if (p.getName().Equals(name))
                    {
                        return p;
                    }
                }
            }

            return null;
        }

        //private void parse()
        //{
        //    System.IO.DirectoryInfo dirInfo = new System.IO.DirectoryInfo(_Name);
        //    foreach (FileInfo file in dirInfo.GetFiles())
        //    {
        //        Part part = Part.createPart(file.Name);
        //        part.FilePath = file.FullName;
        //        if (null != part)
        //        {
        //            this._parts.Add(part);
        //        }
        //    }

        //    foreach (DirectoryInfo dir in dirInfo.GetDirectories())
        //    {
        //        if (dir.Name == "_rels")
        //        {
        //            parseRelationships(dir.FullName);
        //        }
        //        else
        //        {
        //            Package pack = new Package(dir.Name);
        //            this._packages.Add(pack);
        //        }
        //    }
        //}

        //private void parseRelationships(String relFile)
        //{
        //    String[] rels = System.IO.Directory.GetFiles(relFile, "*.rels");

        //}
    }
}
