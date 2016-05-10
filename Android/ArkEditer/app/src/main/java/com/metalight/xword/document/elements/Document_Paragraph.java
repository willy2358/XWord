package com.metalight.xword.document.elements;

import java.util.ArrayList;
import java.util.List;

import com.metalight.xword.utils.StrokeTrackManager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

//paragraph, there is no CR(\r\n) in the paragraph text. 
public class Document_Paragraph {
	
	private List<TextSegment> segments = new ArrayList<TextSegment>();
	
	public void Parse(String paraText)
	{
		String txtSeg = paraText;
		TextSegment seg = new TextSegment();
		Paint p = new Paint();	
		p.setColor(Color.RED);
		p.setTextSize(20.0f);
		
		seg.setText(txtSeg, new PointF(10.0f, 10.0f), p);
		
		segments.add(seg);
		
	}

    public void Draw(Canvas canvas)
    {
    	for(TextSegment seg: segments)
    	{
    		seg.Draw(canvas);
    	}
    }
    
    public boolean parseEditTrack(StrokeTrackManager track)
    {
    	for(TextSegment seg : segments)
    	{
    		if (seg.parseEditTrack(track))
    		{
    			return true;
    		}
    	}
    	return false;
    }

	public TextLine[] GetTextLinesInBound(Rect bound) {
		
		for(TextSegment seg : this.segments){
			TextLine[] lines = seg.getTextLinesInBound(bound);
			if (null != lines && lines.length > 0){
				return lines;
			}
		}
		return null;
	}

	public boolean parseSegments(String content) {
		String txtSeg = content;
		TextSegment seg = new TextSegment();
		Paint p = new Paint();	
		p.setColor(Color.RED);
		p.setTextSize(20.0f);
		
		seg.setText(txtSeg, new PointF(10.0f, 10.0f), p);
		
		segments.add(seg);
		
		return true;
	}
}
