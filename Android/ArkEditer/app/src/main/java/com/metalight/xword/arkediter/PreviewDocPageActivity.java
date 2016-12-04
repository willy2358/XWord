package com.metalight.xword.arkediter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.metalight.xword.utils.Config;

public class PreviewDocPageActivity extends AppCompatActivity {
    private DocPagePanel _editResultPanel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_doc_page);

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.layout_preview_doc_page);
        LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
        int docId = intent.getIntExtra(ActivityInteraction.DOC_ID, 1);

        _editResultPanel = new DocPagePanel(this);
        _editResultPanel.setLayoutParams(lap);
        _editResultPanel.setMinimumHeight(1500);
        _editResultPanel.setMinimumWidth(300);
        _editResultPanel.setDocId(docId);
        _editResultPanel.setGetPageDataUrl(String.format("http://%s/api/PreviewDocChanges/?", Config.getServerAddress()));
        layout.addView(_editResultPanel);

        int startIdx = Config.getDocLastEditPageIndex(docId);
        _editResultPanel.displayPageContent(startIdx);
    }

    public void OnBackToEdit(View view){
        Intent intent = new Intent(this, EditDocPageActivity.class);
//			int docId = 1;
//			intent.putExtra(DocViewer.DOC_ID, docId);

        startActivity(intent);
    }
}
