using System;
using System.Collections.Generic;
using System.Linq;
using System.Messaging;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Word = Microsoft.Office.Interop.Word;

namespace DocxConverter
{
    class Program
    {
        private const string MESSAGE_QUEUE_NAME = @".\Private$\DocxTasks";
        static void Main(string[] args)
        {
            UInt64 count = 0;
            MessageQueue mq = GetMessageQueue();
            if (null == mq)
            {
                System.Console.WriteLine("Create Message Queue Failed:" + MESSAGE_QUEUE_NAME);
                return;
            }

            while(true)
            {
                Tuple<string, string> param = WaitForMessage(mq);
                if (null != param)
                {
                    ConvertDocxToXps(param.Item1, param.Item2);
                }

                if (WaitForUserExitCommand())
                {
                    mq.Close();
                    break;
                }

                System.Console.WriteLine("{0} times waiting", count++);
            }
        }

        private static bool WaitForUserExitCommand()
        {
            try
            {
                string name = UserActionReader.ReadLine(300);
                if (name == "x")
                {
                    return true;
                }
            }
            catch (TimeoutException ex)
            {
                System.Console.WriteLine(ex.Message);
            }

            return false;
        }

        private static void ConvertDocxToXps(string docx, string xps)
        {
            try
            {
                Word.Application wordApp = new Word.Application();
                Word.Document document = wordApp.Documents.Open(docx);
                Object Nothing = Missing.Value;
                document.SaveAs(xps, Word.WdExportFormat.wdExportFormatXPS);
                document.Close(ref Nothing, ref Nothing, ref Nothing);
                wordApp.Quit();
            }
            catch(Exception ex)
            {
                System.Console.WriteLine("----------Convert docx exception:" + ex.Message);
            }
        }
        private static Tuple<string, string> WaitForMessage( MessageQueue mq)
        {
            if (null == mq)
            {
                return null;
            }
            try
            {
                Message msg = mq.Receive(new TimeSpan(0, 0, 5));
                msg.Formatter = new XmlMessageFormatter(new String[] { "System.String,mscorlib" });
                
                Console.WriteLine("**************Received message:" + msg.Label.ToString() + " -- " + msg.Body.ToString());
                string[] parts = msg.Body.ToString().Split('-');
                return new Tuple<string, string>(parts[0], parts[1]);
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
            return null;
        }

        private static MessageQueue GetMessageQueue()
        {
            try
            {
                if (!MessageQueue.Exists(MESSAGE_QUEUE_NAME))
                {
                    return MessageQueue.Create(MESSAGE_QUEUE_NAME);
                }
                else
                {
                    return new MessageQueue(MESSAGE_QUEUE_NAME);
                }
            }
            catch(Exception)
            {
                return null;
            }

        }
    }
}
