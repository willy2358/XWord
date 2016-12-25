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
            string data = "{\"ErrorMsg\":\"OK\",\"Docs\":[";
            foreach(var v in docs)
            {
                //data += string.Format("{\"DocName\":\"{0}\",\"DocId\":\"{1}\"},", v.Key, v.Value);
                data += "{\"DocName\":" + "\"" + v.Key + "\"," + "\"DocId\":" + "\"" + v.Value + "\"},";
            }
            data = data.TrimEnd(',') + "]}";
            HttpResponseMessage result = new HttpResponseMessage { Content = new StringContent(data, Encoding.GetEncoding("UTF-8"), "application/json") };
            return result;
        }

    }
}
