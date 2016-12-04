package com.metalight.xword.arkediter;

//import java.io.FileInputStream;
//
//import com.metalight.document.types.Document;

import com.metalight.xword.utils.Config;

import android.os.Bundle;
import android.sax.RootElement;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.app.Activity;
//import android.content.Intent;
//import android.view.Menu;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.RelativeLayout;

//import android.R;

public class DocViewer extends Activity {
	public final static  String DOC_ID = "DocId";
	private DocPagePanel_Editable _pageEditPanel = null;
	private DocPagePanel _editResultPanel = null;
	RelativeLayout _panelLayout = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_viewer);
		
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.panelLayout);
		LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		_panelLayout = layout;

//		DocPagePanel uiComp = new DocPagePanel(this);
//		//RelativeLayout layout = (RelativeLayout)findViewById(R.id.relLayout);
//
//		uiComp.setLayoutParams(lap);
//		uiComp.setMinimumHeight(500);
//		uiComp.setMinimumWidth(300);
//		layout.addView(uiComp);


		Intent intent = getIntent();
		int docId = intent.getIntExtra(DocViewer.DOC_ID, 1);
		_pageEditPanel = new DocPagePanel_Editable(this);
		_pageEditPanel.setId(R.id.edit_doc_panel);
		_pageEditPanel.setLayoutParams(lap);
		_pageEditPanel.setMinimumHeight(1500);
		_pageEditPanel.setMinimumWidth(300);
		_pageEditPanel.setDocId(docId);
		_pageEditPanel.setGetPageDataUrl(String.format("http://%s/api/getDocPages/?",Config.getServerAddress()));
		_pageEditPanel.setEditCommandUrl(String.format("http://%s/api/editDocPage/?",Config.getServerAddress()));
		layout.addView(_pageEditPanel);

//		EditText edit = new EditText(this);
//		edit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
//		edit.setImeOptions(EditorInfo.IME_ACTION_DONE);
//		edit.setWidth(500);
//		edit.setHeight(100);
//		//textV.setGravity(Gravity.LEFT);
//		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//		layoutParams2.setMargins(100, 300, 0, 0);
//		edit.setLayoutParams(layoutParams2);
//		edit.setText("Result 33333333333333333");
//		layout .addView(edit);
		_editResultPanel = new DocPagePanel(this);
		_editResultPanel.setLayoutParams(lap);
		_editResultPanel.setMinimumHeight(1500);
		_editResultPanel.setMinimumWidth(300);
		_editResultPanel.setDocId(docId);
		_editResultPanel.setGetPageDataUrl(String.format("http://%s/api/PreviewDocChanges/?",Config.getServerAddress()));

        int startIdx = Config.getDocLastEditPageIndex(docId);
		_pageEditPanel.displayPageContent(startIdx);
		_editResultPanel.displayPageContent(startIdx);
	}

	public  void backToEditPanel(View view){
		_panelLayout.removeView(_editResultPanel);
		_panelLayout.addView(_pageEditPanel);

		_pageEditPanel.invalidate();
	}
	public  void previewEditResults(View view)
	{
		_panelLayout.removeView(_pageEditPanel);
		_panelLayout.addView(_editResultPanel);
		//_editResultPanel.invalidate();
		_editResultPanel.updatePageContent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doc_viewer, menu);
		return true;
	}
}
