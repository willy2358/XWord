package com.metalight.xword.arkediter;

//import java.io.FileInputStream;
//
//import com.metalight.document.types.Document;

import com.metalight.xword.document.types.Document;

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

//import android.R;

public class DocViewer extends Activity {

	private PagePanel pagePanel = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_viewer);
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layoutDocView);
		LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		pagePanel = new PagePanel(this);
		pagePanel.setLayoutParams(lap);
		pagePanel.setMinimumHeight(1500);
		pagePanel.setMinimumWidth(300);
		
		Intent intent = getIntent();
		String file = intent.getStringExtra(StartPage.EXTRA_CUR_DOC_NAME);
		Document doc = Document.CreateDocument(file);
		if (null != doc){
			pagePanel.setDocument(doc);
		}
		
		layout.addView(pagePanel);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doc_viewer, menu);
		return true;
	}

}
