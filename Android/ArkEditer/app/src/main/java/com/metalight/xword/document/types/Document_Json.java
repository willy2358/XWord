package com.metalight.xword.document.types;

import com.metalight.xword.document.elements.Document_Page;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by willy on 2016/5/19.
 */
public class Document_Json extends Document{

    private JSONArray _jsonPages = null;
    public Document_Json(String fileName) {
        super(fileName);
    }

    public  void setPagesJson(JSONArray jsonPages){
        _jsonPages = jsonPages;
    }
    @Override
    public boolean loadContents() {

        try{
            for(int i = 0; i < _jsonPages.length(); i++) {
                JSONObject jsonPage = _jsonPages.getJSONObject(i);
                Document_Page page = new Document_Page();
                page.parseParagraphs(jsonPage);
                this.pages.add(page);
            }
            return  true;
        }
        catch (JSONException e){
            return  false;
        }
    }
}
