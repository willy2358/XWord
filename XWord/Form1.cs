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

namespace XWord
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            string docx = System.IO.Path.Combine(Application.StartupPath, "1.docx");
            string xpsFile = System.IO.Path.Combine(Application.StartupPath, "1.xps");
            XDocument xdoc = new XDocument(docx);
            xdoc.parsePagesFromXPS(xpsFile);

            xdoc.PrintPages();

            //OPCPackage docOpc = new OPCPackage(docx);
            //Part docPart = docOpc.getEntryPart();
            
            //string xpsFile = ConvertDocxIntoXPS(docx);
            
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
            Word.Application application = new Word.Application();
            Word.Document document = application.Documents.Open(docx);
            string xpsName = Path.GetFileNameWithoutExtension(docx) + ".xps";
            string xpsFile = System.IO.Path.Combine(Path.GetDirectoryName(docx), xpsName);
            document.SaveAs(xpsFile, ".xps");
            document.Close();
            return xpsFile;
        }
    }
}
