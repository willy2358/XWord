using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XWordService_MVC.Controllers;
using System.Net.Http;

namespace XWordService_MVC.Tests.Controllers
{
    [TestClass]
    public class PreviewDocChangesControllerTest
    {
        [TestMethod]
        public void TestPreviewDocChanges()
        {
            //GetDocPagesController controller = new GetDocPagesController();
            //HttpResponseMessage response = controller.Get(1, 1, 2);

            //string data = await response.Content.ReadAsStringAsync();
            ////JavaScriptArray array = (JavaScriptArray)JavaScriptConvert.DeserializeObject(context.Request.Form[0]);

            //Assert.IsNotNull(data);
            PreviewDocChanges();
        }

        public async void PreviewDocChanges()
        {
            PreviewDocChangesController controller = new PreviewDocChangesController();
            HttpResponseMessage response = controller.Get(1, 0, 1);

            string data = await response.Content.ReadAsStringAsync();
            ////JavaScriptArray array = (JavaScriptArray)JavaScriptConvert.DeserializeObject(context.Request.Form[0]);

            //Assert.IsNotNull(data);
            string data2 = data;
        }
    }
}
