using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using XWordService_MVC.Models;

namespace XWordService_MVC.Controllers
{
    public class UploadDocController : ApiController
    {
        //
        // GET: /UploadCoc/

        public async Task<HttpResponseMessage> PostFile()
        {
            if (!Request.Content.IsMimeMultipartContent())
            {
                throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
            }

            string root = DocumentManager.GetOriginDocsSavePath();
            if (string.IsNullOrEmpty(root))
            {
                return null;
            }
            var provider = new MultipartFormDataStreamProvider(root);
            try
            {
                // Read the form data and return an async task.
                await Request.Content.ReadAsMultipartAsync(provider);
                var fileName = provider.FormData.GetValues("fileName")[0];
                string[] ps = fileName.Split('\\');
                var file = provider.FileData;
                string fileLocalName = file[0].LocalFileName;
                recordLoadedFile(ps[ps.Length - 1], fileLocalName);
                return new HttpResponseMessage()
                {
                    Content = new StringContent("OK")
                };
            }
            catch (System.Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
            }
        }

        private void recordLoadedFile(string fileOriginName, string savedFile)
        {
            string savedDocFile = savedFile + ".docx";
            try
            {
                System.IO.File.Move(savedFile, savedDocFile);
                DocumentManager.SaveUploadedDocumentRecord(fileOriginName, savedFile);
            }
            catch(Exception)
            {

            }
            
        }
    }
}
