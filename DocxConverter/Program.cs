using System;
using System.Collections.Generic;
using System.Linq;
using System.Messaging;
using System.Text;
using System.Threading.Tasks;
using Word = Microsoft.Office.Interop.Word;

namespace DocxConverter
{
    class Program
    {
        static void Main(string[] args)
        {
            int count = 0;
            while(true)
            {
                Message msg = null;
                MessageQueue mq = null;
                try
                {
                    if (!MessageQueue.Exists(@".\Private$\TechRepublic"))
                    {
                        mq = MessageQueue.Create(@".\Private$\TechRepublic");
                    }
                    else
                    {
                        mq = new MessageQueue(@".\Private$\TechRepublic");
                    }
                    mq = new MessageQueue(@".\Private$\TechRepublic");
                    msg = mq.Receive(new TimeSpan(0, 0, 3));
                    msg.Formatter = new XmlMessageFormatter(new String[] { "System.String,mscorlib" });
                    mq.Receive();
                    Console.WriteLine(msg.Label.ToString() + " -- " + msg.Body.ToString());
                }
                catch (System.Messaging.MessageQueueException ex)
                {
                    Console.WriteLine("MSMQ Error: " + ex.ToString());
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Error: " + ex.ToString());
                }
                finally
                {
                    //mq.Close();
                }
                System.Console.WriteLine("{0} times waiting", count++);

                try
                {
                    string name = UserActionReader.ReadLine(3000);
                    if (name == "x")
                    {
                        mq.Close();
                        break;
                    }
                }
                catch (TimeoutException ex)
                {
                    System.Console.WriteLine(ex.Message);
                }

            }


        }
    }
}
