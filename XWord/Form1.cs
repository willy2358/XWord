using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

using Word = Microsoft.Office.Interop.Word;
using soox.opc;
using soox.ooxml.core;
using soox.user;
using System.Reflection;
using XWord.soox.app;

namespace XWord
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            //string xpsFile = System.IO.Path.Combine(Application.StartupPath, "1.xps");
            //XDocument xdoc = new XDocument(docx);
            //xdoc.parsePagesFromXPS(xpsFile);

            //xdoc.PrintPages();

            //OPCPackage docOpc = new OPCPackage(docx);
            //Part docPart = docOpc.getEntryPart();
            
            
            //OPCPackage xpsOpc = new OPCPackage(xpsFile);

            //Part part = xpsOpc.GetXpsFixedDocPart();

            //List<IBlock> blocks = part.getContents();
            //int pageNum = blocks.Count;
            //for(int i = 0; i < blocks.Count; i++)
            //{
            //    PageContent page = blocks[i] as PageContent;

            //    string pagePartPath = part.getZipDir() + page.GetMyPagePartPath();

            //    Part pagePart = xpsOpc.GetPartByPath(pagePartPath);

            //}

            
        }

        private string ConvertDocxIntoXPS(string docx)
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

        private void button1_Click(object sender, EventArgs e)
        {
            TestPreprocessDocument();

        }

        private void TestConvertDocxToXps()
        {
            string docx = System.IO.Path.Combine(Application.StartupPath, "1.docx");
            string xpsFile = ConvertDocxIntoXPS(docx);
        }

        private void TestPreprocessDocument()
        {
            string docx = System.IO.Path.Combine(Application.StartupPath, "1.docx");
            Workspace workspace = new Workspace(docx);
            workspace.PreprocessForEditDocument();
        }
    }
}
