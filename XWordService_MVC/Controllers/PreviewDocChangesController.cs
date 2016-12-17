using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;
using XWord.soox.app;
using XWordService_MVC.Models;

namespace XWordService_MVC.Controllers
{
    public class PreviewDocChangesController : ApiController
    {
        // GET api/<controller>
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<controller>/5
        public string Get(int id)
        {
            return "value";
        }

        public HttpResponseMessage Get(String docId, int startPageIdx, int endPageIdx)
        {
            Workspace workspace = DocumentManager.GetDocumentWorkspace(docId);
            if (null != workspace)
            {
                List<soox.user.XPage2> pages = workspace.GetPreviewPages(startPageIdx, endPageIdx);
                string data = "{\"ErrorMsg\":\"OK\",\"DocId\":" + docId.ToString() + ",";
                string json = soox.serialize.JsonSerializer.ToJson<soox.user.XPage2>(pages);
                data += "\"Pages\":" + json + "}";
                //return data += "}";
                HttpResponseMessage result = new HttpResponseMessage { Content = new StringContent(data, Encoding.GetEncoding("UTF-8"), "application/json") };
                return result;
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
    }
}