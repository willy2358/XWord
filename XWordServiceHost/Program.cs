using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace XWordServiceHost
{
    class Program
    {
        static void Main(string[] args)
        {
            Type serviceType = typeof(XWordService.DocPageFetchService);
            using (ServiceHost host = new ServiceHost(serviceType))
            {
                host.Open();

                Console.WriteLine("XWordService started");
                Console.ReadKey(true);
                host.Close();
            }
        }
    }
}
