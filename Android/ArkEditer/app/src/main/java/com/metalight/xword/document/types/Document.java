package com.metalight.xword.document.types;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.metalight.xword.document.elements.Document_Page;


public abstract class Document {
	
	protected List<Document_Page> pages = new ArrayList<Document_Page>();
	public Document(String fileName)
	{
		this.fileName = fileName;
	}
	protected int _currentPageIdx = 0;
	
	@SuppressLint("DefaultLocale")
	public static Document CreateDocument(String filename)
	{
		String ext = filename.substring(filename.length() - 3);
		Document doc = null;
		if ( 0 == ext.compareToIgnoreCase("txt")){
			Document_PlainText textDoc = new Document_PlainText(filename);
			if (textDoc.loadContents()){
				doc = textDoc;
			}
		}
		else if (0 == ext.compareToIgnoreCase("doc")){
			Document_OfficeWord officeDoc = new Document_OfficeWord(filename);
			if (officeDoc.loadContents()){
				doc = officeDoc;
			}
		}
		else if (0 == ext.compareToIgnoreCase("docx")){
			
		}
		
		if (null != doc){
			doc.setFileName(filename);	
		}
		
		return doc;
		
	}
	
	public abstract boolean loadContents();
	
	public void draw(Canvas canvas){
		for(Document_Page page : this.pages){
			page.Draw(canvas);
		}
	}
	
	public void setFileName(String filename)
	{
		this.fileName = filename;
	}
	
	public String getFileName()
	{
		return this.fileName;
		
	}

	public void setCurrentPageIdx(int pageIdx){
		this._currentPageIdx = pageIdx;
		//to to trigger to redraw
	}

	public Document_Page getCurrentPage()
	{
		return null;
	}
	
	
	private String fileName;

}
