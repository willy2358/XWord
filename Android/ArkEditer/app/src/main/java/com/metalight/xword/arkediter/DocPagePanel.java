package com.metalight.xword.arkediter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.ParcelUuid;
import android.view.View;

import com.metalight.xword.document.elements.Document_Page;
import com.metalight.xword.document.types.Document;
import com.metalight.xword.document.types.Document_Json;
import com.metalight.xword.utils.Config;
import com.metalight.xword.utils.ErrorCode;
import com.metalight.xword.utils.HttpTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by willy on 2016/9/27.
 */
public class DocPagePanel extends View {

    protected static int PAGE_CACHE_COUNT = 5;
    protected Document_Json document = new Document_Json("");
    protected int _docId;
    protected String _getPageDataApiUrl;
    protected int _currentPageIdx = 0;

    public DocPagePanel(Context context){
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if (null != document){
            Document_Page page = this.document.getPage(_currentPageIdx);
            if (null != page)
            {
                page.Draw(canvas);
            }
        }
        //TestDraw(canvas);
    }

    private void TestDraw(Canvas canvas) {
        Paint style = new Paint();
        style.setColor(Color.GREEN);
        int i = 0;
        Paint style2 = new Paint();
        style2.setColor(Color.BLUE);

        Paint styleT = new Paint();
        styleT.setColor(Color.RED);
        styleT.setTextSize(40);
        int size = 300;
        for(int x = 0; x < 5000; x += size){
            for(int y = 0; y < 5000; y +=size){

                String text = String.format("%d,%d",x,y);
                if (i % 2 == 0){
                    canvas.drawText(text, x, y, styleT);

                    canvas.drawRect(x,y, x+size, y+size, style);
                }
                else{
                    canvas.drawText(text, x, y, styleT);
                    canvas.drawRect(x,y, x+size, y+size, style2);
                }
                i++;
            }
        }
    }

    public  void displayPageContent(int pageIdx){
        _currentPageIdx = pageIdx;
        if (!document.hasPageFetched(pageIdx))
        {
            fetchPageContent(pageIdx, pageIdx + PAGE_CACHE_COUNT);
        }

        this.invalidate();
    }

    protected void updateDocument(JSONArray jsonPages)
    {
        this.document.updatePages(jsonPages);
        this.invalidate();
    }

    protected   void fetchDocEditedPagesDataAsync(int docId, int startPageIdx, int endPageIdx) {
        HttpTask task = new HttpTask();
        task.setTaskHandler(new HttpTask.HttpTaskHandler() {
            public void taskSuccessful(String json) {
                try {
                    JSONObject jObject = new JSONObject(json);
                    if (0 == jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)) {
                        updateDocument(jObject.getJSONArray("Pages"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void taskFailed() {
            }
        });

        task.execute(makeFetchPageDataUrl(docId,startPageIdx, endPageIdx ));
    }

    public void fetchPageContent(int startPageIdx, int endPageIdx){
        fetchDocEditedPagesDataAsync(_docId, startPageIdx, endPageIdx);
    }

    public void setDocId(int docId) {
        this.document.setDocmentId(docId);
        this._docId = docId;
    }

    public void setGetPageDataUrl(String url){ this._getPageDataApiUrl = url;}

    private String makeFetchPageDataUrl(int docId, int startPageIdx, int endPageIdx){
        String url = String.format("%sdocId=%d&startPageIdx=%d&endPageIdx=%d",
               _getPageDataApiUrl, docId, startPageIdx, endPageIdx );

        return  url;
    }
}


