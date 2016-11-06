package com.metalight.xword.document.elements;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.metalight.xword.document.types.Document;

import org.json.JSONArray;
import org.json.JSONObject;

public class Document_Page {
	private  int _pageNumber = 0;
	private List<Document_Paragraph> paragraphs = new ArrayList<Document_Paragraph>();
	private Document _parentDocument;
	public void Draw(Canvas canvas){
		for(Document_Paragraph para : paragraphs){
			para.Draw(canvas);
		}
	}

	public void setParentDocument(Document doc){
		_parentDocument = doc;
	}

	public  Document getParentDocument(){
		return _parentDocument;
	}

	public  int getPageNumber()
	{
		return _pageNumber;
	}
	public boolean parseParagraphs(String contents){
		Document_Paragraph para = new Document_Paragraph(this);
		if (para.parseSegments(contents)){
			this.paragraphs.add(para);
			return true;
		}
		
		return false;
	}

	public  boolean parseParagraphs(JSONObject jsonPage){
		try {
			_pageNumber = jsonPage.getInt("pageNumber");
			JSONArray blocks = jsonPage.getJSONArray("lineBlocks");
			for(int i = 0; i < blocks.length(); i++)
			{
				JSONObject block = blocks.getJSONObject(i);
				Document_Paragraph para = new Document_Paragraph(this);
				para.parse(block);
				this.paragraphs.add(para);
			}
		}
		catch (Exception ex)
		{
			return  false;
		}
		return true;
	}

	
	public List<Document_Paragraph> getParagraphs(){
		return this.paragraphs;
	}

	public TextLine[] GetTextLinesInBound(Rect bound){
		for(Document_Paragraph para : this.paragraphs){
			TextLine[] lines = para.GetTextLinesInBound(bound);
			if (null != lines && lines.length > 0){
				return lines;
			}
		}
		
		return null;
	}
}
