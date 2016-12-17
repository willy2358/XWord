using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using XWordService_MVC.Models;

namespace XWordService_MVC.Controllers
{
    public class GetUserDocsController : ApiController
    {
        //
        // GET: /GetUserDocs/

        public HttpResponseMessage Get()
        {
            Dictionary<string, string> docs = DocumentManager.GetUploadedDocuments();
            string data = "{\"ErrorMsg\":\"OK\",\"Docs\":";
            string json = soox.serialize.JsonSerializer.DictToJson(docs);
            data += json + "}";
            HttpResponseMessage result = new HttpResponseMessage { Content = new StringContent(data, Encoding.GetEncoding("UTF-8"), "application/json") };
            return result;
        }

    }
}
