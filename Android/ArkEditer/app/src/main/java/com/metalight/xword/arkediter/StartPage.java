package com.metalight.xword.arkediter;

import com.metalight.xword.utils.CallbackBundle;
import com.metalight.xword.utils.OpenFileDialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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
	public  void OnClickTest(View view)
	{
		networkTest();;
		//TestWebService2();
	}

	private void TestWebService1() {
		String SERVICE_NS = "http://localhost:8733/";
		String SERVICE_URL = "http://localhost:8733/XWordService/DocPageFetchService/";
		String methodName = "GetData";
//创建httpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
//使用soap1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//实例化SoapObject对象
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
/**
 * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
 * */
		request.addProperty("value", "111");
//将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
		envelope.bodyOut = request;
		try{
//调用webService
			ht.call(null, envelope);
//txt1.setText("看看"+envelope.getResponse());
			if(envelope.getResponse() != null){
				//txt2.setText("有返回");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				//txt1.setText("返回值 = "+name);
			}else{
				//txt2.setText("无返回");
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.d(e.toString(), "OnClickTest: ");
		}
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
