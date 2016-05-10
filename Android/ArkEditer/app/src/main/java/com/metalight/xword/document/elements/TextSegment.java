package com.metalight.xword.document.elements;


import java.util.ArrayList;
import java.util.List;

import com.metalight.xword.utils.StrokeTrackManager;

import android.graphics.*;
public class TextSegment {
	
    //private float text_size = 12.0f;
   
    private float page_width = 720;
    
    private PointF para_offset;
    
    private Paint text_style;
    
    private float line_span = 20;
    
    private List<TextLine> text_lines = new ArrayList<TextLine>();
	
	public void setText(String text, PointF offset, Paint textStyle)
	{
		this.text_style = textStyle;
		this.para_offset = offset;
		parseLines(text);
	}
	
    public boolean parseEditTrack(StrokeTrackManager track)
    {
    	for(TextLine line : text_lines)
    	{
    		if (line.parseEditTrack(track))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
	public void Draw(Canvas canvas)
	{
		for(TextLine line: text_lines)
		{
			line.Draw(canvas);
		}
	}
	private void parseLines(String paraText)
	{
		int posHead = 0;
		int posEnd = 1;
		
		float charWidth = text_style.getTextSize();
		float line_offset_x = para_offset.x;
		float line_offset_y = para_offset.y;
		
		TextLine lastLine = null;
		while(posHead < paraText.length() - 1)
		{
			float space =  page_width - line_offset_x;
			float remain_space = space;
			StringBuilder sb = new StringBuilder();
			
			while(remain_space > charWidth && posHead < paraText.length() - 1)
			{
				int chars_num = (int)(remain_space / charWidth);
				
				posEnd = posHead + chars_num;
			    if (posEnd > paraText.length() - 1)
			    {
			    	posEnd = paraText.length() - 1;
			    }
				
				sb.append(paraText.substring(posHead, posEnd));
				
				Rect rect = new Rect();
				text_style.getTextBounds(sb.toString(), 0, sb.length(), rect);

				remain_space = space - rect.width();
				posHead = posEnd;
			}
			
			TextLine textLine = new TextLine(sb.toString());
			
			if (null != lastLine)
			{
				textLine.setPrevTextLine(lastLine);
			}
			textLine.setPostion(new PointF(line_offset_x, line_offset_y), text_style);
			text_lines.add(textLine);
			lastLine = textLine;
			
			line_offset_x = 0;
			line_offset_y += charWidth + line_span;
			
		}
		
	}

	public TextLine[] getTextLinesInBound(Rect bound) {
		List<TextLine> lines = new ArrayList<TextLine>();
		for(TextLine line : this.text_lines){
			if (line.inBound(bound)){
				lines.add(line);
			}
		}
		
		return lines.toArray(new TextLine[0]);
	}
}
