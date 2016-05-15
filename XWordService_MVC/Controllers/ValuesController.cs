using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace XWordService_MVC.Controllers
{
    public class ValuesController : ApiController
    {
        // GET api/values
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/values/5
        public string Get(int id)
        {
            return id.ToString();
        }

        public string Get(string p1, string p2)
        {
            return string.Format("p1:{0}, p2:{2}", p1, p2);
        }

        public string TestA(int id, string p1, string p2)
        {
            return string.Format("p1:{0}, p2:{2}", p1, p2);
        }
        //public 

        // POST api/values
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}