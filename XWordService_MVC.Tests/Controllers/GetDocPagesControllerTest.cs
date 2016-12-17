using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XWordService_MVC.Controllers;
using System.Net.Http;

namespace XWordService_MVC.Tests.Controllers
{
    [TestClass]
    public class GetDocPagesControllerTest
    {
        [TestMethod]
        public void TestGetDocPages()
        {
            //GetDocPagesController controller = new GetDocPagesController();
            //HttpResponseMessage response = controller.Get(1, 1, 2);

            //string data = await response.Content.ReadAsStringAsync();
            ////JavaScriptArray array = (JavaScriptArray)JavaScriptConvert.DeserializeObject(context.Request.Form[0]);

            //Assert.IsNotNull(data);
            TestGetDocPages2();
        }

        public async void TestGetDocPages2()
        {
            string docId = "BodyPart_7d72e65e-9b83-4b15-a1be-210113f348df";
            GetDocPagesController controller = new GetDocPagesController();
            HttpResponseMessage response = controller.Get(docId, 0, 1);

            string data = await response.Content.ReadAsStringAsync();
            ////JavaScriptArray array = (JavaScriptArray)JavaScriptConvert.DeserializeObject(context.Request.Form[0]);

            //Assert.IsNotNull(data);
            string data2 = data;
        }

    }
}
