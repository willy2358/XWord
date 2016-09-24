using soox.user;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using XWord.soox.app;
using XWordService_MVC.Models;

namespace XWordService_MVC.Controllers
{
    public class EditDocPageController : ApiController
    {
        //
        // GET: /EditDocPage/

        //public ActionResult Index()
        //{
        //    //return View();
        //}

//        EditTrack:
//EditType:1/2/3/4(del/insert/replace)


        /// <summary>
        /// 
        /// </summary>
        /// <param name="docId"></param>
        /// <param name="pageIdx"></param>
        /// <param name="runId"></param>
        /// <param name="oldPartText"></param>
        /// <param name="newPartText"></param>
        /// <param name="editTrack">x,y;x,y[;x,y][;x,y]...</param>
        /// <param name="editType">EditType:1/2/3/4(del/insert/replace)</param>
        /// <returns></returns>

        public HttpResponseMessage Get(int docId, int pageIdx, int runId, 
                                       int editType, string oldPartText, string newPartText, string editTrack)
        {
            //XDocument doc = DocumentManager.GetDocument(docId);
            //bool ret = false;
            //if (null != doc)
            //{
            //    ret = doc.UpdatePageText(pageIdx, runId, (XDocument.Edit_Type)editType, oldPartText, newPartText, editTrack);
            //}

            //if (ret)
            //{
            //    string data = "{\"ErrorMsg\":\"OK\"}";
            //    return new HttpResponseMessage { Content = new StringContent(data, Encoding.GetEncoding("UTF-8"), "application/json") };
            //}
            //else
            //{
            //    return null;
            //}

            bool ret = false;
            Workspace workspace = DocumentManager.GetEdittingDocumentWorkspace(docId);
            if (null != workspace)
            {
                ret = workspace.UpdatePageText(pageIdx, runId, (XDocument.Edit_Type)editType, oldPartText, newPartText, editTrack);
            }

            if (ret)
            {
                string data = "{\"ErrorMsg\":\"OK\"}";
                return new HttpResponseMessage { Content = new StringContent(data, Encoding.GetEncoding("UTF-8"), "application/json") };
            }
            else
            {
                return null;
            }
        }

    }
}
