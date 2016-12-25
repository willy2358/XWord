using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XWordService_MVC.Controllers;
using System.Net.Http;

namespace XWordService_MVC.Tests.Controllers
{
    [TestClass]
    public class GetUserDocsControllerTest
    {
        [TestMethod]
        public void TestGetUserDocs()
        {
            TestGetUserDocs_proxy();
        }

        private async void TestGetUserDocs_proxy()
        {
            GetUserDocsController controller = new GetUserDocsController();
            HttpResponseMessage response = controller.Get();

            string data = await response.Content.ReadAsStringAsync();
            ////JavaScriptArray array = (JavaScriptArray)JavaScriptConvert.DeserializeObject(context.Request.Form[0]);

            //Assert.IsNotNull(data);
            string data2 = data;
        }
    }
}
