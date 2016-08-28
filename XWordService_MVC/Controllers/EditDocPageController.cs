using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Mvc;

namespace XWordService_MVC.Controllers
{
    public class EditDocPageController : Controller
    {
        //
        // GET: /EditDocPage/

        public ActionResult Index()
        {
            return View();
        }

//        EditTrack:
//EditType:1/2/3/4(del/insert/replace)


        /// <summary>
        /// 
        /// </summary>
        /// <param name="docId"></param>
        /// <param name="pageIdx"></param>
        /// <param name="runId"></param>
        /// <param name="originPartText"></param>
        /// <param name="newPartText"></param>
        /// <param name="editTrack">x,y;x,y[;x,y][;x,y]...</param>
        /// <param name="editType">EditType:1/2/3/4(del/insert/replace)</param>
        /// <returns></returns>

        public HttpResponseMessage Get(int docId, int pageIdx, int runId, 
                                       int editType, string originPartText, string newPartText, string editTrack)
        {
            return null;
        }

    }
}
