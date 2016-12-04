package com.metalight.xword.arkediter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metalight.xword.utils.Config;

/**
 * Created by willy on 2016/12/4.
 */
public class EditPageView extends RelativeLayout {

    public final static  String DOC_ID = "DocId";
    private DocPagePanel_Editable _pageEditPanel = null;
    private int _docId;
    LayoutInflater mInflater;
    public EditPageView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        //init();
    }
    public EditPageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }
    public EditPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void loadDocument(int docId){
        View v = mInflater.inflate(R.layout.edit_page_view, this, true);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.edit_page_layout);
        RelativeLayout.LayoutParams lap = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lap.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        lap.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        lap.leftMargin = 0;
        lap.topMargin = 0;

        _pageEditPanel = new DocPagePanel_Editable(this.getContext());
        _pageEditPanel.setId(R.id.edit_doc_panel);
        _pageEditPanel.setLayoutParams(lap);
        _pageEditPanel.setMinimumHeight(1500);
        _pageEditPanel.setMinimumWidth(300);
        _pageEditPanel.setDocId(docId);
        _pageEditPanel.setGetPageDataUrl(String.format("http://%s/api/getDocPages/?", Config.getServerAddress()));
        _pageEditPanel.setEditCommandUrl(String.format("http://%s/api/editDocPage/?",Config.getServerAddress()));
        int startIdx = Config.getDocLastEditPageIndex(docId);
        _pageEditPanel.displayPageContent(startIdx);
        layout.addView(_pageEditPanel);
    }

    public void init() {

    }

    public void createTextInputEditText() {
        View v = mInflater.inflate(R.layout.edit_page_view, this, true);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.edit_page_layout);
        EditText edit = new EditText(this.getContext());
        edit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edit.setWidth(200);
        edit.setHeight(20);
        LayoutParams layoutParams2 = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams2.leftMargin =0;
        layoutParams2.topMargin = 0;
        edit.setLayoutParams(layoutParams2);
        edit.setText("Result 333333333333");
        layout .addView(edit);
    }
}
