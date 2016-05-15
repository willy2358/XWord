package com.metalight.xword.arkediter;

import com.metalight.xword.utils.CallbackBundle;
import com.metalight.xword.utils.HttpTask;
import com.metalight.xword.utils.OpenFileDialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;


/*import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;*/

public class StartPage extends Activity {
	static final String nameSpaceWeather = "http://WebXml.com.cn/";
	static final String urlWeather
			= "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
	static final String methodWeather = "getWeather";
	static final String soapActionWeather = "http://WebXml.com.cn/getWeather";
	///手机归属地Webservice的参数信息
	static final String nameSpaceAddress = "http://WebXml.com.cn/";
	static final String urlAddress
			= "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
	static final String methodNameAddress = "getMobileCodeInfo";
	static final String soapActionAddress
			= "http://WebXml.com.cn/getMobileCodeInfo";

	public final static String EXTRA_CUR_DOC_NAME = "Current Document Name";
	
	public final static String lastEditDoc = "/data/data/t3.txt";
	
	public static int openFileDlgId = 0x1100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page);
		
//        // ���õ�����ťʱ���ļ��Ի���  
//		Button btnOpenNew = (Button)findViewById(R.id.btnOpenNewDoc);
//		
//		btnOpenNew.setOnClickListener(new OnClickListener()
//		{
//			public void onClick(View view)
//			{
//				showDialog(openFileDlgId);
//			}
//		});
//		
////        btnOpenNew.setOnClickListener(new OnClickListener() {  
////           @Override  
////            public void onClick(View arg0) {  
////                showDialog(openfileDialogId);  
////            }  
////        }); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_page, menu);
		return true;
	}

	public void OnClickOpenNewDoc(View view)
	{

		Intent intent = new Intent(this, DocViewer.class);     
		String file = lastEditDoc;
		intent.putExtra(EXTRA_CUR_DOC_NAME, file);  
		startActivity(intent); 
	}
    private  void networkTest()
	{
		/*HttpClient httpClient ;
		HttpGet httpget;
		HttpResponse httpResponse;*/
		//URL url = new URL("http://www.baidu.com");
		try {
			URL url = new URL("http://localhost:8733/XWordService/DocPageFetchService/?wsdl");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();


			conn.setConnectTimeout(6 * 1000);
			conn.setRequestMethod("POST");
			if (conn.getResponseCode() != 200)    //从Internet获取网页,发送请求,将网页以流的形式读回来

				throw new RuntimeException("请求url失败");


			InputStream is = conn.getInputStream();
			//String result = readData(is, "GBK"); //文件流输入出文件用outStream.write
			conn.disconnect();
		}
		catch (Exception e)
		{
			String error = e.getMessage();
			String err2 = error;
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
	}

	private  void testParseJson()
	{
		String json = "{\"ErrorMsg\":\"OK\",\"DocId\":1,\"Pages\":[{\"pageOrder\":1,\"lineBlocks\":[{\"Position\":{\"X\":126.02,\"Y\":92.904},\"Text\":\"软件介绍\"},{\"Position\":{\"X\":89.784,\"Y\":114.86},\"Text\":\"街景平台数据入货检查工具，程序名称\"},{\"Position\":{\"X\":269.21,\"Y\":114.86},\"Text\":\"StreetViewDataChe\"},{\"Position\":{\"X\":358.51,\"Y\":114.86},\"Text\":\"cher\"},{\"Position\":{\"X\":379.51,\"Y\":114.86},\"Text\":\".exe\"},{\"Position\":{\"X\":400.51,\"Y\":114.86},\"Text\":\", \"},{\"Position\":{\"X\":411.07,\"Y\":114.86},\"Text\":\"用于对街景平台的入\"},{\"Position\":{\"X\":68.784,\"Y\":130.46},\"Text\":\"货数据进行规格检查。检查项包括：图像文\"},{\"Position\":{\"X\":258.29,\"Y\":130.46},\"Text\":\"件与矢量文件是否一致、矢量文件是否包含指定字段、\"},{\"Position\":{\"X\":68.784,\"Y\":146.06},\"Text\":\"字段数据类型是否符合约定、指定字段的数据是否合乎范围以及指定检查字段的数据是否异常\"},{\"Position\":{\"X\":68.784,\"Y\":161.66},\"Text\":\"（所有记录\"},{\"Position\":{\"X\":121.34,\"Y\":161.66},\"Text\":\"在\"},{\"Position\":{\"X\":131.9,\"Y\":161.66},\"Text\":\"此字段的数据都相同即为异常\"},{\"Position\":{\"X\":268.49,\"Y\":161.66},\"Text\":\"）\"},{\"Position\":{\"X\":278.93,\"Y\":161.66},\"Text\":\"。\"},{\"Position\":{\"X\":68.784,\"Y\":186.98},\"Text\":\"软件\"},{\"Position\":{\"X\":126.02,\"Y\":346.61},\"Text\":\"双击\"},{\"Position\":{\"X\":147.14,\"Y\":346.61},\"Text\":\"运行\"},{\"Position\":{\"X\":170.78,\"Y\":346.61},\"Text\":\"StreetViewDataChecker.exe\"},{\"Position\":{\"X\":301.97,\"Y\":346.61},\"Text\":\",\"},{\"Position\":{\"X\":307.25,\"Y\":346.61},\"Text\":\"工具的运行界面\"},{\"Position\":{\"X\":380.83,\"Y\":346.61},\"Text\":\"如下：\"},{\"Position\":{\"X\":111.02,\"Y\":580.63},\"Text\":\"数据格式的支持的选项有：“\"},{\"Position\":{\"X\":242.57,\"Y\":580.63},\"Text\":\"高德单反\"},{\"Position\":{\"X\":284.45,\"Y\":580.63},\"Text\":\"”、“\"},{\"Position\":{\"X\":308.33,\"Y\":580.63},\"Text\":\"Ladybug5\"},{\"Position\":{\"X\":350.35,\"Y\":580.63},\"Text\":\"-\"},{\"Position\":{\"X\":355.63,\"Y\":580.63},\"Text\":\"入货\"},{\"Position\":{\"X\":376.63,\"Y\":580.63},\"Text\":\"”、“\"},{\"Position\":{\"X\":400.39,\"Y\":580.63},\"Text\":\"天下图\"},{\"Position\":{\"X\":431.83,\"Y\":580.63},\"Text\":\"”和“\"},{\"Position\":{\"X\":458.38,\"Y\":580.63},\"Text\":\"Ladybug5\"},{\"Position\":{\"X\":500.38,\"Y\":580.63},\"Text\":\"-\"},{\"Position\":{\"X\":90.024,\"Y\":596.23},\"Text\":\"拼接成果\"},{\"Position\":{\"X\":132.14,\"Y\":596.23},\"Text\":\"”。\"},{\"Position\":{\"X\":126.02,\"Y\":621.07},\"Text\":\"软件\"},{\"Position\":{\"X\":156.02,\"Y\":621.07},\"Text\":\"使用\"},{\"Position\":{\"X\":129.38,\"Y\":652.06},\"Text\":\"输入数据格式约定\"},{\"Position\":{\"X\":126.74,\"Y\":674.26},\"Text\":\"街景平台数据入货检查工具（\"},{\"Position\":{\"X\":258.41,\"Y\":674.26},\"Text\":\"StreetViewDataChecker.exe\"},{\"Position\":{\"X\":389.59,\"Y\":674.26},\"Text\":\"），可检查的数据格式有：\"},{\"Position\":{\"X\":137.54,\"Y\":689.86},\"Text\":\"高德自研单反数据\"},{\"Position\":{\"X\":137.54,\"Y\":705.46},\"Text\":\"Ladybug5\"},{\"Position\":{\"X\":181.22,\"Y\":705.46},\"Text\":\"入货数据\"},{\"Position\":{\"X\":137.54,\"Y\":721.06},\"Text\":\"Ladybug5\"},{\"Position\":{\"X\":181.22,\"Y\":721.06},\"Text\":\"拼接成果数据\"},{\"Position\":{\"X\":137.54,\"Y\":736.66},\"Text\":\"天下图（\"},{\"Position\":{\"X\":179.66,\"Y\":736.66},\"Text\":\"Peacemap\"},{\"Position\":{\"X\":223.97,\"Y\":736.66},\"Text\":\"）\"},{\"Position\":{\"X\":90.024,\"Y\":752.26},\"Text\":\"各数据的格式约定如下：\"}]}]}";

		try
		{
			JSONObject jObject = new JSONObject(json);

			String st = jObject.getString("ErrorMsg");
			JSONArray pages = jObject.getJSONArray("Pages");
			int num = pages.length();
		}
		catch (JSONException ex)
		{
			ex.printStackTrace();;
		}
	}
	private  void networkTest2()
	{
		HttpTask task = new HttpTask();
		task.setTaskHandler(new HttpTask.HttpTaskHandler(){
			public void taskSuccessful(String json) {
				try {
					JSONObject jObject = new JSONObject(json);

					String st = jObject.getString("ErrorMsg");
					JSONArray pages = jObject.getJSONArray("Pages");
					int num = pages.length();

				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}

			public void taskFailed() {
			}
		});

		String localIp = getLocalIpAddress();

		Log.e("IP", localIp);
		localIp = "10.0.2.2";
		task.execute("http://" + localIp +":8088/api/getDocPages/?docId=1&startPageIdx=1&endPageIdx=1");
		//task.execute("http://118.244.208.16/jessica/hardware/show");

	}
	public  void TestWebService2()
	{
		//String nameSpaceWeather = "";
		//String methodWeather = "";
		//String urlWeather = "";
		System.out.println("××1××进入getWeather方法");
		SoapObject soapObject = new SoapObject(nameSpaceWeather, methodWeather);
		///这边1960是江苏如皋的地区码，这个也是通过这个webservice的方法查的
		soapObject.addProperty("theCityCode", "1960");
		///免费用户userid为空
		soapObject.addProperty("userId", "");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);
		HttpTransportSE httpTransportSE = new HttpTransportSE(urlWeather);
		System.out.println("××2××基本服务设置完毕，下面开始调用服务");
		try
		{
			httpTransportSE.call(soapActionWeather, envelope);
			System.out.println("××3××调用webservice服务成功");

			SoapObject object = (SoapObject) envelope.bodyIn;
			System.out.println("××5××获得服务数据成功");
			String txtWeather = object.getProperty(0).toString();
			System.out.println("××6××解析服务数据成功，数据为："+txtWeather);
			System.out.println("××7××向主线程发送消息，显示天气信息");
			//handlerWeather.sendEmptyMessage(0);
			System.out.println("××8××向主线程发送消息成功，getWeather函数执行完毕");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("××4××调用webservice服务失败");
		}


	}

	private String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("Wifi IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 获取Android本机MAC
	 *
	 * @return
	 */
/*	private String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) this.getContext().getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}*/
	public  void OnClickTest(View view)
	{
		networkTest2();
		//TestWebService2();
		//testParseJson();
	}



	//@override
	protected Dialog OnCreateDialog(int id)
	{
		if (id == openFileDlgId)
		{
		    Dialog dialog = OpenFileDialog.createDialog(id, this, "���ļ�", new CallbackBundle() {  
	                @Override  
	                public void callback(Bundle bundle) {  
	                    String filepath = bundle.getString("path");  
	                    setTitle(filepath); // ���ļ�·����ʾ�ڱ�����  
               }  
	            });  
	            return dialog;  

		}
		else
		{
			return null;
		}
	}
}
