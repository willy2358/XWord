package com.metalight.xword.arkediter;

import com.metalight.xword.utils.CallbackBundle;
import com.metalight.xword.utils.Config;
import com.metalight.xword.utils.ErrorCode;
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
		try {

			Intent intent = new Intent(this, EditDocPageActivity.class);
			int docId = 1;
			intent.putExtra(ActivityInteraction.DOC_ID, docId);
			startActivity(intent);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private void LoadDocPages(JSONArray pages){

	}


	private  void networkTest2()
	{
		HttpTask task = new HttpTask();
		task.setTaskHandler(new HttpTask.HttpTaskHandler(){
			public void taskSuccessful(String json) {
				try {
					JSONObject jObject = new JSONObject(json);
					if (ErrorCode.ERROR_OK == jObject.getString("ErrorMsg")){
						LoadDocPages(jObject.getJSONArray("Pages"));
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
			public void taskFailed() {
			}
		});
        int docId = 1;
		String server = Config.getServerAddress();
		int startIdx = Config.getDocLastEditPageIndex(docId);
		int endIdx = startIdx + Config.getBatchFetchPageNumber() - 1;
		task.execute(getDocPagesQueryUrl(docId,startIdx, endIdx ));
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

	private String getDocPagesQueryUrl(int docId, int startPageIdx, int endPageIdx){
		String url = String.format("http://%s/api/getDocPages/?docId=%d&startPageIdx=%d&endPageIdx=%d",
				                  Config.getServerAddress(), docId, startPageIdx, endPageIdx );

		return  url;
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
