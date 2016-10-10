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

    public boolean updatePages(JSONArray jsonPages)
    {
        try{
            for(int i = 0; i < _jsonPages.length(); i++) {
                JSONObject jsonPage = _jsonPages.getJSONObject(i);
                Document_Page page = new Document_Page();
                page.parseParagraphs(jsonPage);
                updatePage(page);
            }
            return  true;
        }
        catch (JSONException e){
            return  false;
        }
    }

    private void updatePage(Document_Page page)
    {
//        int insertPos = -1;
//        if (page.getPageOrder() < this.pages.get(0).getPageOrder())
//        {
//            insertPos = 0;
//        }
//        else if (page.getPageOrder() > this.pages.get(this.pages.size() - 1).getPageOrder())
//        {
//            insertPos = -1;
//        }
//
//        boolean update = false;
//        for(int i = 0; i < this.pages.size() - 1; i++)
//        {
//            if (pages.get(i).getPageOrder() == page.getPageOrder())
//            {
//                insertPos = i;
//            }
//        }
    }
}
