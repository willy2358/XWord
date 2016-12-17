using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Messaging;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace XWord.soox.app
{
    public class DocxToXpsConverter_MSMQ : DocxToXpsConverter
    {
        private const int WAIT_TIMEOUT_COUNT = 30;
        private const string MESSAGE_QUEUE_NAME = @".\Private$\DocxTasks";
        private const string CONVERTER_EXE = "DocxConverter.exe";
        public override bool Convert(string docx, string xps)
        {
            MakeSureTheConverterProcessIsRunning();

            SendConvertMessage(docx, xps);

            return WaitForMessageResult(xps);
        }

        private void MakeSureTheConverterProcessIsRunning()
        {
            System.Diagnostics.Process[] ps = System.Diagnostics.Process.GetProcessesByName(CONVERTER_EXE);
            if (null == ps || ps.Length < 1)
            {
                string assemblyFilePath = Assembly.GetExecutingAssembly().Location;
                string assemblyDirPath = Path.GetDirectoryName(assemblyFilePath);
                string exePath = System.IO.Path.Combine(assemblyDirPath, CONVERTER_EXE);
                try
                {
                    System.Diagnostics.Process.Start(exePath);
                }
                catch(Exception)
                {

                }
            }
        }

        private bool WaitForMessageResult(string resultXpsFile)
        {
            int count = 0;
            while(count < WAIT_TIMEOUT_COUNT)
            {
                if (System.IO.File.Exists(resultXpsFile))
                {
                    return true;
                }

                count++;
                System.Threading.Thread.Sleep(500);
            }

            return false;
        }
        private void SendConvertMessage(string docx, string xps)
        {
            Message msg = null;
            MessageQueue mq = null;

            try
            {
                msg = new Message();
                msg.Priority = MessagePriority.Normal;
                if (!MessageQueue.Exists(MESSAGE_QUEUE_NAME))
                {
                    mq = MessageQueue.Create(MESSAGE_QUEUE_NAME);
                }
                else
                {
                    mq = new MessageQueue(MESSAGE_QUEUE_NAME);
                }
                msg.Label = "DocxConvert";
                msg.Body = docx + "->" + xps;
                mq.Send(msg);
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
                mq.Close();
            }
        }
    }
}
