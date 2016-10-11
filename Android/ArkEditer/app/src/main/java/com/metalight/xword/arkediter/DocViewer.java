package com.metalight.xword.arkediter;

//import java.io.FileInputStream;
//
//import com.metalight.document.types.Document;

import com.metalight.xword.document.types.Document_Json;
import com.metalight.xword.utils.Config;
import com.metalight.xword.utils.ErrorCode;
import com.metalight.xword.utils.HttpTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
	private DocPagePanel_Editable _pageEditPanel = null;
	private DocPagePanel _editResultPanel = null;
	LinearLayout _layout = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_viewer);
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layoutDocView);
		LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		_layout = layout;

		Intent intent = getIntent();
		int docId = intent.getIntExtra(DocViewer.DOC_ID, 1);
		_pageEditPanel = new DocPagePanel_Editable(this);
		_pageEditPanel.setLayoutParams(lap);
		_pageEditPanel.setMinimumHeight(1500);
		_pageEditPanel.setMinimumWidth(300);
		_pageEditPanel.setDocId(docId);
		_pageEditPanel.setGetPageDataUrl(String.format("http://%s/api/getDocPages/?",Config.getServerAddress()));

		_editResultPanel = new DocPagePanel(this);
		_editResultPanel.setLayoutParams(lap);
		_editResultPanel.setMinimumHeight(1500);
		_editResultPanel.setMinimumWidth(300);
		_editResultPanel.setDocId(docId);
		_editResultPanel.setGetPageDataUrl(String.format("http://%s/api/PreviewDocChanges/?",Config.getServerAddress()));
		//_editResultPanel.setVisibility(View.INVISIBLE);
		//layout.addView(_editResultPanel);
		layout.addView(_pageEditPanel);


        int startIdx = Config.getDocLastEditPageIndex(docId);
		_pageEditPanel.displayPageContent(startIdx);
		//_pageEditPanel.invalidate();
//		int endIdx = startIdx + Config.getBatchFetchPageNumber() - 1;
//		fetchDocOriginPagesDataAsync(docId, startIdx, endIdx);

/*		Document doc = Document.CreateDocument(file);
		if (null != doc){
			_pageEditPanel.setDocument(doc);
		}*/
		

	}

	public  void backToEditPanel(View view){
		_layout.removeView(_editResultPanel);
		_layout.addView(_pageEditPanel);

		_pageEditPanel.invalidate();
	}
	public  void previewEditResults(View view)
	{
		_layout.removeView(_pageEditPanel);
		_layout.addView(_editResultPanel);
		_editResultPanel.invalidate();

		//fetchDocEditedPagesDataAsync(1,0,0);
		//_pageEditPanel.setVisibility(View.INVISIBLE);

		//_editResultPanel.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doc_viewer, menu);
		return true;
	}

//	private  void renderPagesText(JSONArray jsonPages){
//		Document_Json docJson = new Document_Json("");
//		docJson.setPagesJson(jsonPages);
//		docJson.loadContents();
//		_pageEditPanel.setDocument(docJson);
//	}

//	private void renderEditedPages(JSONArray jsonPages){
//			Document_Json docJson = new Document_Json("");
//			docJson.setPagesJson(jsonPages);
//			docJson.loadContents();
//			_editResultPanel.setDocument(docJson);
//	}

//	private  void fetchDocEditedPagesDataAsync(int docId, int startPageIdx, int endPageIdx)
//	{
//		HttpTask task = new HttpTask();
//		task.setTaskHandler(new HttpTask.HttpTaskHandler(){
//			public void taskSuccessful(String json) {
//				try {
//					JSONObject jObject = new JSONObject(json);
//					if (0 == jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)){
//						renderEditedPages(jObject.getJSONArray("Pages"));
//					}
//				}
//				catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//			public void taskFailed() {
//			}
//		});
///*		int docId = 1;
//		String server = Config.getServerAddress();
//		int startIdx = Config.getDocLastEditPageIndex(docId);
//		int endIdx = startIdx + Config.getBatchFetchPageNumber() - 1;*/
//		task.execute(getDocEditedPagesQueryUrl(docId,startPageIdx, endPageIdx ));
//	}
//
//	private  void fetchDocOriginPagesDataAsync(int docId, int startPageIdx, int endPageIdx)
//	{
//		HttpTask task = new HttpTask();
//		task.setTaskHandler(new HttpTask.HttpTaskHandler(){
//			public void taskSuccessful(String json) {
//				try {
//					JSONObject jObject = new JSONObject(json);
//					if (0 == jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)){
//						renderPagesText(jObject.getJSONArray("Pages"));
//					}
//				}
//				catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//			public void taskFailed() {
//			}
//		});
///*		int docId = 1;
//		String server = Config.getServerAddress();
//		int startIdx = Config.getDocLastEditPageIndex(docId);
//		int endIdx = startIdx + Config.getBatchFetchPageNumber() - 1;*/
//		task.execute(getDocOriginPagesQueryUrl(docId,startPageIdx, endPageIdx ));
//	}
//
//	//ex url: http://localhost:8088/api/getDocPages/?docId=1&startPageIdx=0&endPageIdx=0
//	private String getDocOriginPagesQueryUrl(int docId, int startPageIdx, int endPageIdx){
//		String url = String.format("http://%s/api/getDocPages/?docId=%d&startPageIdx=%d&endPageIdx=%d",
//				Config.getServerAddress(), docId, startPageIdx, endPageIdx );
//
//		return  url;
//	}
//
//	//ex upload edit: http://localhost:8088/api/EditDocPage/?docId=1&pageIdx=0&runId=1&editType=3&oldPartText=童年&newPartText=亲戚&editTrack=1,2
//	//ex url:http://localhost:8088/api/PreviewDocChanges/?docId=1&startPageIdx=0&endPageIdx=0
//	private String getDocEditedPagesQueryUrl(int docId, int startPageIdx, int endPageIdx){
//		String url = String.format("http://%s/api/PreviewDocChanges/?docId=%d&startPageIdx=%d&endPageIdx=%d",
//				Config.getServerAddress(), docId, startPageIdx, endPageIdx );
//
//		return  url;
//	}
}
