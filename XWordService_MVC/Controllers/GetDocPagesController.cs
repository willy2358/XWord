using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;

namespace XWordService_MVC.Controllers
{
    public class GetDocPagesController : ApiController
    {
        private string Docs_base_dir = @"C:\MyWeb\data";
        // GET api/<controller>
        public IEnumerable<string> Get()
        {
            return new string[] { "docId", "startPageIdx", "endPageIdx" };
        }

        //// GET api/<controller>/5
        //public string Get(int docId, int startPageIdx, int endPageIdx)
        //{
        //    string docx = "";
        //    string xpsFile = "";
        //    if (GetDocFilesDocId(docId, out docx, out xpsFile))
        //    {
        //        soox.user.XDocument doc = new soox.user.XDocument(docx);
        //        doc.parsePagesFromXPS(xpsFile);
        //        List<soox.user.XPage2> pages = doc.GetPages(startPageIdx, endPageIdx);
        //        string data = "{\"ErrorMsg\":\"OK\",\"DocId\":" + docId.ToString() + ",";
        //        string json = soox.serialize.JsonSerializer.ToJson<soox.user.XPage2>(pages);
        //        data += "\"Pages\":" + json;
        //        return data += "}";

        //        //return "{\"aaa\":\"bbb\"}";
        //    }
        //    else
        //    {
        //        return "Error:GetDocPagesData";
        //    }
        //}

        // GET api/<controller>/5
        public HttpResponseMessage Get(int docId, int startPageIdx, int endPageIdx)
        {
            string docx = "";
            string xpsFile = "";
            if (GetDocFilesDocId(docId, out docx, out xpsFile))
            {
                soox.user.XDocument doc = new soox.user.XDocument(docx);
                doc.parsePagesFromXPS(xpsFile);
                List<soox.user.XPage2> pages = doc.GetPages(startPageIdx, endPageIdx);
                string data = "{\"ErrorMsg\":\"OK\",\"DocId\":" + docId.ToString() + ",";
                string json = soox.serialize.JsonSerializer.ToJson<soox.user.XPage2>(pages);
                data += "\"Pages\":" + json + "}";
                //return data += "}";
                HttpResponseMessage result = new HttpResponseMessage { Content = new StringContent(data, Encoding.GetEncoding("UTF-8"), "application/json") };
                return result;
                //return "{\"aaa\":\"bbb\"}";
            }
            else
            {
                return null;
            }
        }


        // POST api/<controller>
        public void Post([FromBody]string value)
        {
        }

        // PUT api/<controller>/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/5
        public void Delete(int id)
        {
        }

        private bool GetDocFilesDocId(int docId, out string docFile, out string xpsFile)
        {
            docFile = System.IO.Path.Combine(Docs_base_dir, "1.docx");
            xpsFile = System.IO.Path.Combine(Docs_base_dir, "1.xps");

            return true;
        }
    }
}