using soox.user;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Messaging;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Word = Microsoft.Office.Interop.Word;

namespace XWord.soox.app
{
    public class Workspace
    {
        
        private XDocument _editDocument;
        private XDocument _previewDocument;
        private string _originalDocFile;

        public Workspace(string originalDocFile)
        {
            this._originalDocFile = originalDocFile;
        }

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

        public static bool ConvertDocxIntoXPS(string docx, string destXps, bool deleteExistXps = false)
        {
            if (File.Exists(destXps))
            {
                if (deleteExistXps)
                {
                    try
                    {
                        File.Delete(destXps);
                    }
                    catch (Exception)
                    {
                        return false;
                    }
                }
                else
                {
                    return true;
                }
            }

            DocxToXpsConverter converter = DocxToXpsConverter.CreateConverter();
            if (converter.Convert(docx, destXps))
            {
                return true;
            }

            return false;
        }

        private static string GetXpsFilePathForDocx(string docx)
        {
            string xpsName = Path.GetFileNameWithoutExtension(docx) + ".xps";
            string xpsFile = System.IO.Path.Combine(Path.GetDirectoryName(docx), xpsName);

            return xpsFile;
        }

        private static string ConvertDocxIntoXPSViaWordAPI(string docx)
        {
            Word.Application wordApp = new Word.Application();
            Word.Document document = wordApp.Documents.Open(docx);
            Object Nothing = Missing.Value;

            string xpsName = Path.GetFileNameWithoutExtension(docx) + ".xps";
            string xpsFile = System.IO.Path.Combine(Path.GetDirectoryName(docx), xpsName);
            document.SaveAs(xpsFile, Word.WdExportFormat.wdExportFormatXPS);
            document.Close(ref Nothing, ref Nothing, ref Nothing);
            wordApp.Quit();
            return xpsFile;
        }

        public List<XPage2> GetEditPages(int startPageIdx, int endPageIdx)
        {
            if (null != this._editDocument)
            {
                return this._editDocument.GetPages(startPageIdx, endPageIdx);
            }

            return null;
        }

        public List<XPage2> GetPreviewPages(int startPageIdx, int endPageIdx)
        {
            if (null != this._previewDocument)
            {
                return this._previewDocument.GetPages(startPageIdx, endPageIdx);
            }

            return null;
        }

        public bool UpdatePageText(int pageIdx, int runId, XDocument.Edit_Type editType, string oldText, string newText, string editTrack)
        {
            if (null == this._previewDocument)
            {
                if (!PreprocessForPreviewChanges())
                {
                    return false;
                }
            }

            if (_previewDocument.UpdatePageText(pageIdx, runId, (XDocument.Edit_Type)editType, oldText, newText, editTrack))
            {
                string previewDocFile = GetPreviewDocumentName(_originalDocFile);
                _previewDocument.saveAs(previewDocFile, true);
                string previewXps = GetXpsFilePathForDocx(previewDocFile);
                if(ConvertDocxIntoXPS(previewDocFile, previewXps, true))
                {
                    this._previewDocument = new XDocument(previewDocFile);
                    this._previewDocument.parsePagesFromXPS(previewXps);
                    return true;
                }
            }

            return false;
        }
         
        public bool PreprocessForPreviewChanges()
        {
            string previewDocFile = GetPreviewDocumentName(_originalDocFile);
            if (!File.Exists(previewDocFile))
            {
                if (!PreprocessForEditDocument())
                {
                    return false;
                }

                string editDocFile = GetEditDocumentName(_originalDocFile);
                File.Copy(editDocFile, previewDocFile);
            }

            string xpsForPreview = GetXpsFilePathForDocx(previewDocFile);
            if (!File.Exists(xpsForPreview))
            {
                if (!ConvertDocxIntoXPS(previewDocFile, xpsForPreview))
                {
                    return false;
                }
            }

            this._previewDocument = new XDocument(previewDocFile);
            this._previewDocument.parsePagesFromXPS(xpsForPreview);
            return true;
        }

        public bool PreprocessForEditDocument()
        {
            string editDocFile = GetEditDocumentName(_originalDocFile);
            if (!File.Exists(editDocFile))
            {
                XDocument doc = new XDocument(_originalDocFile);
                doc.ParseContents();
                doc.SetupIdForRuns();
                doc.saveAs(editDocFile);
            }

            string xpsForEdit = GetXpsFilePathForDocx(editDocFile);
            if (!File.Exists(xpsForEdit))
            {
                if (!ConvertDocxIntoXPS(editDocFile, xpsForEdit))
                {
                    return false;
                }
            }

            this._editDocument = new XDocument(editDocFile);
            this._editDocument.parsePagesFromXPS(xpsForEdit);
            return true;

            //if (File.Exists(editDocFile))
            //{
            //    string editXps = ConvertDocxIntoXPS(editDocFile);
            //    this._editDocument = new XDocument(editDocFile);
            //    this._editDocument.parsePagesFromXPS(editXps);
            //}
            //string previewDocFile = GetPreviewDocumentName(_originalDocFile);
            //if (File.Exists(previewDocFile))
            //{
            //    try
            //    {
            //        File.Delete(previewDocFile);
            //    }
            //    catch(Exception)
            //    {

            //    }
            //}
            //File.Copy(editDocFile, previewDocFile);
            //if (File.Exists(previewDocFile))
            //{
            //    string previewXps = ConvertDocxIntoXPS(previewDocFile);
            //    this._previewDocument = new XDocument(previewDocFile);
            //    this._previewDocument.parsePagesFromXPS(previewXps);
            //}
        }
    }
}
