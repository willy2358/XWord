using soox.user;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace XWordService_MVC.Controllers
{
    public static class DocumentManager
    {
        private static string Docs_base_dir = @"C:\MyWeb\data";
        private static Dictionary<int, XDocument> OpenedDocuments = new Dictionary<int, XDocument>();
        public static XDocument GetDocument(int docId)
        {
            if (OpenedDocuments.Keys.Contains(docId))
            {
                return OpenedDocuments[docId];
            }
            else
            {
                string docx = "";
                string xpsFile = "";
                if (GetDocFilesDocId(docId, out docx, out xpsFile))
                {
                    soox.user.XDocument doc = new soox.user.XDocument(docx);
                    doc.parsePagesFromXPS(xpsFile);
                    OpenedDocuments.Add(docId, doc);

                    return doc;
                }
                else
                {
                    return null;
                }
            }
        }


        private static bool GetDocFilesDocId(int docId, out string docFile, out string xpsFile)
        {
            docFile = System.IO.Path.Combine(Docs_base_dir, "1.docx");
            xpsFile = System.IO.Path.Combine(Docs_base_dir, "1.xps");

            return true;
        }
    }
}