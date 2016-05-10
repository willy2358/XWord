using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;


namespace XWordService
{
    // 注意: 使用“重构”菜单上的“重命名”命令，可以同时更改代码和配置文件中的类名“Service1”。
    public class DocPageFetchService : IDocPageFetchService
    {
        private string Docs_base_dir = @"C:\Users\willy\Documents\Visual Studio 2013\Projects\XWord\XWord\bin\Debug";
        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            if (composite == null)
            {
                throw new ArgumentNullException("composite");
            }
            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }
            return composite;
        }


        public string GetDocPagesData(int docId, int startPageIdx, int endPageIdx)
        {
            string docx = "";
            string xpsFile = "";
            if (GetDocFilesDocId(docId, out docx, out xpsFile))
            {
                soox.user.XDocument doc = new soox.user.XDocument(docx);
                doc.parsePagesFromXPS(xpsFile);
                List<soox.user.XPage2> pages = doc.GetPages(startPageIdx, endPageIdx);
                string json = soox.serialize.JsonSerializer.ToJson<soox.user.XPage2>(pages);

                return json;
            }
            else
            {
                return "Error:GetDocPagesData";
            }

        }

        private bool GetDocFilesDocId(int docId, out string docFile, out string xpsFile)
        {
            docFile = System.IO.Path.Combine(Docs_base_dir, "1.docx");
            xpsFile = System.IO.Path.Combine(Docs_base_dir, "1.xps");

            return true;
        }

        
    }
}
