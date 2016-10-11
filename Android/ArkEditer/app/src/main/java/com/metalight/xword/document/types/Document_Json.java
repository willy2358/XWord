package com.metalight.xword.document.types;

import com.metalight.xword.document.elements.Document_Page;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                this._pages.add(page);
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
            for(int i = 0; i < jsonPages.length(); i++) {
                JSONObject jsonPage = jsonPages.getJSONObject(i);
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
        List<Document_Page> pages = new ArrayList<Document_Page>();

        if ( _pages.size() < 1 || page.getPageOrder() < this._pages.get(0).getPageOrder())
        {
            pages.add(page);
            pages.addAll(_pages);
        }
        else if (page.getPageOrder() > this._pages.get(this._pages.size() - 1).getPageOrder())
        {
            pages.addAll(_pages);
            pages.add(page);
        }
        else
        {
            insertPageIntoMiddle(page, pages);
        }
       _pages = pages;
    }

    private void insertPageIntoMiddle(Document_Page page, List<Document_Page> pages) {
        int i = 0;
        for(; i < _pages.size() - 1; i++)
        {
            Document_Page tmpPage =  _pages.get(i);
            if (tmpPage.getPageOrder() < page.getPageOrder())
            {
                pages.add(_pages.get(i));
            }
            else
            {
                break;
            }
        }

        pages.add(page);
        if (_pages.get(i).getPageOrder() == page.getPageOrder())
        {
            i++;
        }
        pages.addAll(_pages.subList(i,_pages.size() - i));
    }
}
