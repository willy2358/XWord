using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XWordServiceClient
{
    public partial class Form1 : Form
    {
        //private XWordService.
        private XWordServiceReference1.DocPageFetchServiceClient TClient = new XWordServiceReference1.DocPageFetchServiceClient();
        public Form1()
        {
            InitializeComponent();

            //List<string> obj = new List<string>();

            //Object oo = obj;

            //bool r =( obj is List<String>);
            //if (obj is IList)
            //{

            //}
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //string val = TClient.GetData(3);
            ////System.Diagnostics.Debug.WriteLine(val);

            //XWordServiceReference1.CompositeType type =  TClient.GetDataUsingDataContract(new XWordServiceReference1.CompositeType());
            //System.Diagnostics.Debug.WriteLine(val);
            string ret = TClient.GetDocPagesData(0, 1, 3);
            System.Diagnostics.Debug.WriteLine(ret);
        }
    }
}
