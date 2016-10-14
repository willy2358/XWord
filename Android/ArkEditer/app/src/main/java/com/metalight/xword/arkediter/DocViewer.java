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
		_pageEditPanel.setEditCommandUrl(String.format("http://%s/api/editDocPage/?",Config.getServerAddress()));

		_editResultPanel = new DocPagePanel(this);
		_editResultPanel.setLayoutParams(lap);
		_editResultPanel.setMinimumHeight(1500);
		_editResultPanel.setMinimumWidth(300);
		_editResultPanel.setDocId(docId);
		_editResultPanel.setGetPageDataUrl(String.format("http://%s/api/PreviewDocChanges/?",Config.getServerAddress()));
		layout.addView(_pageEditPanel);


        int startIdx = Config.getDocLastEditPageIndex(docId);
		_pageEditPanel.displayPageContent(startIdx);
		_editResultPanel.displayPageContent(startIdx);
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doc_viewer, menu);
		return true;
	}
}
