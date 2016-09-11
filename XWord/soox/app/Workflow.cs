using soox.user;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XWord.soox.app
{
    class Workflow
    {
        private static string GetEditDocumentName(string originalDocName)
        {
            string path = System.IO.Path.GetDirectoryName(originalDocName);
            string filename = System.IO.Path.GetFileNameWithoutExtension(originalDocName);
            string ext = System.IO.Path.GetExtension(originalDocName);

            return  System.IO.Path.Combine(path, string.Format("{0}_e{1}", filename, ext));

        }

        private static string GetPreviewDocumentName(string originalDocName)
        {
            string path = System.IO.Path.GetDirectoryName(originalDocName);
            string filename = System.IO.Path.GetFileNameWithoutExtension(originalDocName);
            string ext = System.IO.Path.GetExtension(originalDocName);

            return System.IO.Path.Combine(path, string.Format("{0}_e_prev{1}", filename, ext));
        }

        public static void PreprocessOriginalDocument(string originalDocFile)
        {
            XDocument doc = new XDocument(originalDocFile);
            doc.ParseContents();
            doc.SetupIdForRuns();
            doc.saveAs(GetEditDocumentName(originalDocFile));
        }
    }
}
