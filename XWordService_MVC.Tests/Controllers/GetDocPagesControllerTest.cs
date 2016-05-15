using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using XWordService_MVC.Controllers;

namespace XWordService_MVC.Tests.Controllers
{
    [TestClass]
    public class GetDocPagesControllerTest
    {
        [TestMethod]
        public void TestGetDocPages()
        {
            GetDocPagesController controller = new GetDocPagesController();
            string json = controller.Get(1, 1, 2);

            Assert.IsNotNull(json);
        }
    }
}
