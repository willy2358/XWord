package com.metalight.xword.arkediter;

//import java.io.FileInputStream;
//
//import com.metalight.document.types.Document;

import com.metalight.xword.document.types.Document;
import com.metalight.xword.document.types.Document_Json;
import com.metalight.xword.utils.Config;
import com.metalight.xword.utils.ErrorCode;
import com.metalight.xword.utils.HttpTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.app.Activity;
//import android.content.Intent;
//import android.view.Menu;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.R;

public class DocViewer extends Activity {
	public final static  String DOC_ID = "DocId";
	private PagePanel _pagePanel = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_viewer);
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layoutDocView);
		LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		_pagePanel = new PagePanel(this);
		_pagePanel.setLayoutParams(lap);
		_pagePanel.setMinimumHeight(1500);
		_pagePanel.setMinimumWidth(300);
		layout.addView(_pagePanel);

		Intent intent = getIntent();
		int docId = intent.getIntExtra(DocViewer.DOC_ID, 1);
		int startIdx = Config.getDocLastEditPageIndex(docId);
		int endIdx = startIdx + Config.getBatchFetchPageNumber() - 1;
		fetchDocPagesDataAsync(docId, startIdx, endIdx);

/*		Document doc = Document.CreateDocument(file);
		if (null != doc){
			_pagePanel.setDocument(doc);
		}*/
		

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doc_viewer, menu);
		return true;
	}

	private  void renderPagesText(JSONArray jsonPages){
		Document_Json docJson = new Document_Json("");
		docJson.setPagesJson(jsonPages);
		docJson.loadContents();
		_pagePanel.setDocument(docJson);
	}

	private  void fetchDocPagesDataAsync(int docId, int startPageIdx, int endPageIdx)
	{
		HttpTask task = new HttpTask();
		task.setTaskHandler(new HttpTask.HttpTaskHandler(){
			public void taskSuccessful(String json) {
				try {
					JSONObject jObject = new JSONObject(json);
					if (0 == jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)){
						renderPagesText(jObject.getJSONArray("Pages"));
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
			public void taskFailed() {
			}
		});
/*		int docId = 1;
		String server = Config.getServerAddress();
		int startIdx = Config.getDocLastEditPageIndex(docId);
		int endIdx = startIdx + Config.getBatchFetchPageNumber() - 1;*/
		task.execute(getDocPagesQueryUrl(docId,startPageIdx, endPageIdx ));
	}

	//ex url: http://localhost:8088/api/getDocPages/?docId=1&startPageIdx=1&endPageIdx=1
	private String getDocPagesQueryUrl(int docId, int startPageIdx, int endPageIdx){
		String url = String.format("http://%s/api/getDocPages/?docId=%d&startPageIdx=%d&endPageIdx=%d",
				Config.getServerAddress(), docId, startPageIdx, endPageIdx );

		return  url;
	}
}
