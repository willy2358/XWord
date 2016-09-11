using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XWordService_MVC.Controllers;
using System.Net.Http;

namespace XWordService_MVC.Tests.Controllers
{
    [TestClass]
    public class EditDocPageControllerTest
    {
        [TestMethod]
        public void TestUpdatePageRun()
        {
            ActualTestProcedure();
        }

        public async void ActualTestProcedure()
        {
            EditDocPageController controller = new EditDocPageController();
            HttpResponseMessage response = controller.Get(1, 0, 1, 3, "童年", "多大", "1,2");

            string data = await response.Content.ReadAsStringAsync();
            ////JavaScriptArray array = (JavaScriptArray)JavaScriptConvert.DeserializeObject(context.Request.Form[0]);

            //Assert.IsNotNull(data);
            string data2 = data;
        }
    }
}
