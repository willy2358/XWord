package com.metalight.xword.document.elements;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Document_Page {
	
	private List<Document_Paragraph> paragraphs = new ArrayList<Document_Paragraph>();
	
	public void Draw(Canvas canvas){
		for(Document_Paragraph para : paragraphs){
			para.Draw(canvas);
		}
	}
	
	public boolean parseParagraphs(String contents){
		Document_Paragraph para = new Document_Paragraph();
		if (para.parseSegments(contents)){
			this.paragraphs.add(para);
			return true;
		}
		
		return false;
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
