using soox.user;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using XWord.soox.app;

namespace XWordService_MVC.Models
{
    public static class DocumentManager
    {
        public static string DOCS_BASE_DIR = @"C:\MyWeb\data";
        public const string ORIGIN_DOC_PATH = "OriginDocs";
        public const String DOC_LIST_FILE = "DocList.txt";

        private static Dictionary<int, XDocument> OpenedDocuments = new Dictionary<int, XDocument>();
        private static Dictionary<int, Workspace> _DocWorkspaces = new Dictionary<int, Workspace>();
        private static Dictionary<String, String> _UploadedDocs = new Dictionary<string, string>();
        //private static Dictionary<int, Workspace> PreviewChangesWorkspaces = new Dictionary<int, Workspace>();

        public static Workspace GetDocumentWorkspace(int docId)
        {
            Workspace workspace = null;
            if (_DocWorkspaces.Keys.Contains(docId))
            {
                workspace = _DocWorkspaces[docId];
            }
            else
            {
                string docFile;
                if (GetDocFileForDocId(docId, out docFile))
                {
                    workspace = new Workspace(docFile);
                    _DocWorkspaces.Add(docId, workspace);
                }
            }

            if (null != workspace)
            {
                workspace.PreprocessForEditDocument();
            }

            return workspace;
        }

        public static String GetOriginDocsSavePath()
        {
            string path = System.IO.Path.Combine(DOCS_BASE_DIR, ORIGIN_DOC_PATH);
            if (!System.IO.Directory.Exists(path))
            {
                try
                {
                    System.IO.Directory.CreateDirectory(path);
                }
                catch(Exception)
                {
                    return "";
                }
            }

            return path;
        }

        public static String GetDocListFile()
        {
            string file = System.IO.Path.Combine(GetOriginDocsSavePath(), DOC_LIST_FILE);
            if (!System.IO.File.Exists(file))
            {
                try
                {
                    FileStream stream = System.IO.File.Create(file);
                    stream.Close();
                }
                catch(Exception)
                {
                    return "";
                }
            }

            return file;
        }

        public static Dictionary<string, string> GetUploadedDocuments()
        {
            if (_UploadedDocs.Count < 1)
            {
                try
                {
                    string[] lines = System.IO.File.ReadAllLines(GetDocListFile(), Encoding.UTF8);
                    foreach(string s in lines)
                    {
                        string[] ps = s.Split(',');
                        if (ps.Length == 2)
                        {
                            AddDocumentRecord(ps[0], ps[1]);
                        }
                    }
                }
                catch(Exception)
                {
                    return null;
                }
            }

            return _UploadedDocs;
        }

        public static bool SaveUploadedDocumentRecord(string originFileName, string savedFileName)
        {
            List<String> lines = new List<string>();
            lines.Add(string.Format("{0},{1}", originFileName, savedFileName));
            try
            {
                System.IO.File.AppendAllLines(GetDocListFile(), lines, Encoding.UTF8);
            }
            catch (Exception)
            {
                return false;
            }

            AddDocumentRecord(originFileName, savedFileName);

            return true;
        }

        private static void AddDocumentRecord(string originFileName, string savedFileName)
        {
            if (_UploadedDocs.Keys.Contains(originFileName))
            {
                _UploadedDocs[originFileName] = savedFileName;
            }
            else
            {
                _UploadedDocs.Add(originFileName, savedFileName);
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
            docFile = System.IO.Path.Combine(DOCS_BASE_DIR, "1.docx");
            xpsFile = System.IO.Path.Combine(DOCS_BASE_DIR, "1.xps");

            return true;
        }

        private static bool GetDocFileForDocId(int docId, out string docFile)
        {
            docFile = System.IO.Path.Combine(DOCS_BASE_DIR, "1.docx");
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