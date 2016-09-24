using soox.user;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using XWord.soox.app;

namespace XWordService_MVC.Models
{
    public static class DocumentManager
    {
        private static string Docs_base_dir = @"C:\MyWeb\data";
        private static Dictionary<int, XDocument> OpenedDocuments = new Dictionary<int, XDocument>();
        private static Dictionary<int, Workspace> EditDocWorkspaces = new Dictionary<int, Workspace>();
        private static Dictionary<int, Workspace> PreviewChangesWorkspaces = new Dictionary<int, Workspace>();

        public static Workspace GetEdittingDocumentWorkspace(int docId)
        {
            if (EditDocWorkspaces.Keys.Contains(docId))
            {
                return EditDocWorkspaces[docId];
            }
            else
            {
                string docFile;
                if (GetDocFileForDocId(docId, out docFile))
                {
                    Workspace ws = new Workspace(docFile);
                    ws.PreprocessForEditDocument();
                    EditDocWorkspaces.Add(docId, ws);
                    return ws;
                }
                else
                {
                    return null;
                }
            }
        }

        public static Workspace GetPreviewDocChangesWorkspace(int docId)
        {
            if (PreviewChangesWorkspaces.Keys.Contains(docId))
            {
                return PreviewChangesWorkspaces[docId];
            }
            else
            {
                string docFile;
                if (GetDocFileForDocId(docId, out docFile))
                {
                    Workspace ws = new Workspace(docFile);
                    ws.PreprocessForPreviewChanges();
                    PreviewChangesWorkspaces.Add(docId, ws);
                    return ws;
                }
                else
                {
                    return null;
                }
            }
        }
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

        private static bool GetDocFileForDocId(int docId, out string docFile)
        {
            docFile = System.IO.Path.Combine(Docs_base_dir, "1.docx");
            return true;

        }

        private static string GetEditDocumentName(string originalDocName)
        {
            string path = System.IO.Path.GetDirectoryName(originalDocName);
            string filename = System.IO.Path.GetFileNameWithoutExtension(originalDocName);
            string ext = System.IO.Path.GetExtension(originalDocName);

            return string.Format("{0}{1}_e.{2}", path, filename, ext);

        }

        private static string GetPreviewDocumentName(string originalDocName)
        {
            string path = System.IO.Path.GetDirectoryName(originalDocName);
            string filename = System.IO.Path.GetFileNameWithoutExtension(originalDocName);
            string ext = System.IO.Path.GetExtension(originalDocName);

            return string.Format("{0}{1}_e_prev.{2}", path, filename, ext);
        }

        private static void PreprocessOriginalDocument(string originalDocFile)
        {
            soox.user.XDocument doc = new soox.user.XDocument(originalDocFile);

            doc.saveAs(GetEditDocumentName(originalDocFile));
        }
    }
}