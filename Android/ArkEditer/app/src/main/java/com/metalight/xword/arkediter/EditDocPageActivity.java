package com.metalight.xword.arkediter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class EditDocPageActivity extends AppCompatActivity {

    private int _docId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc_page);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_edit_doc_page);
        try {

            Intent intent = getIntent();
            int docId = intent.getIntExtra(ActivityInteraction.DOC_ID, 1);
            _docId = docId;
            EditPageView editView = new EditPageView(this);
            LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            editView.setLayoutParams(lap);
            layout.addView(editView);
            editView.loadDocument(docId);
        }
        catch (Exception ex){
            Log.d("sss", "onCreate: " + ex.getMessage());
        }
    }

    public void OnPreview(View view){
        Intent intent = new Intent(this, PreviewDocPageActivity.class);
        intent.putExtra(ActivityInteraction.DOC_ID, _docId);
        startActivity(intent);
    }
}
