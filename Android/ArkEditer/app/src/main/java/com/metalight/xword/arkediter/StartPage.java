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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
	private ListView _docListView;
	private List<Map<String, Object>> mData;

	private List<DocItem> _userDocs = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_start_page);
		//mData = getData();
//		MyAdapter adapter = new MyAdapter(this);
//		setListAdapter(adapter);
		fetchUserDocListAsync();

		_docListView = new ListView(this);
		_docListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DocItem docItem =_userDocs.get(position);
				if (null != docItem){
					openDocument(docItem.DocId);
				}
			}
		});

		setContentView(_docListView);
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
			String docId = "BodyPart_7d72e65e-9b83-4b15-a1be-210113f348df";
			intent.putExtra(ActivityInteraction.DOC_ID, docId);
			startActivity(intent);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private void openDocument(String docId){
		try {
			Intent intent = new Intent(this, EditDocPageActivity.class);
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
        String docId = "ddd";
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

	private String getDocPagesQueryUrl(String docId, int startPageIdx, int endPageIdx){
		String url = String.format("http://%s/api/getDocPages/?docId=%s&startPageIdx=%d&endPageIdx=%d",
				                  Config.getServerAddress(), docId, startPageIdx, endPageIdx );

		return  url;
	}

	private void loadUesrDocList(JSONArray jsonDocs){
        for(int i = 0; i < jsonDocs.length(); i++){
			try {
				JSONObject jsonDoc  = jsonDocs.getJSONObject(i);
				DocItem docItem = new DocItem();
				docItem.DocName = jsonDoc.getString("DocName");
				docItem.DocId = jsonDoc.getString("DocId");
				this._userDocs.add(docItem);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		_docListView.setAdapter(new ArrayAdapter<DocItem>(this, android.R.layout.simple_expandable_list_item_1, _userDocs));
	}

	private  void fetchUserDocListAsync() {
		HttpTask task = new HttpTask();
		task.setTaskHandler(new HttpTask.HttpTaskHandler() {
			public void taskSuccessful(String json) {
				try {
					JSONObject jObject = new JSONObject(json);
					if (0 == jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)) {
						loadUesrDocList(jObject.getJSONArray("Docs"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			public void taskFailed() {
			}
		});

		task.execute(makeGetUserDocsUrl());
	}

	private String makeGetUserDocsUrl(){
		return  String.format("http://%s/api/GetUserDocs",Config.getServerAddress());
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
