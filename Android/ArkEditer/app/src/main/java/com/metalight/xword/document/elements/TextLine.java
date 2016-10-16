package com.metalight.xword.document.elements;

import com.metalight.xword.utils.StrokeTrackManager;
import com.metalight.xword.utils.VectorPoint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

public class TextLine {
	
	private TextLine prev_textline = null;
	private TextLine next_textLine = null;
	private String line_text;
	private PointF line_position;
	private Paint line_style;
	private int _runId;
	private Document_Paragraph _parentParaghaph;
	public TextLine(String lineText) {
		line_text = lineText;
	}

	public void setParentParaghaph(Document_Paragraph para){
		this._parentParaghaph = para;
	}

	public Document_Paragraph getParentParaghaph(){
		return _parentParaghaph;
	}

	public void setRunId(int runId){
		_runId = runId;
	}

	public int getRunId(){
		return _runId;
	}
	public float getBottomY()
	{
		return line_position.y;
	}
	
	public float getTopY()
	{
		return line_position.y - line_style.getTextSize()- 1;
	}
	public String getText(){
		return  line_text;
	}
	public void setPostion(PointF pos, Paint style)
	{
		this.line_position = pos;
		this.line_style = style;
	}

	public void setPrevTextLine(TextLine prevLine)
	{
		prev_textline = prevLine;
		if (null == prevLine.next_textLine)
		{
			prevLine.setNextTextLine(this);
		}
	}
	
    public boolean parseEditTrack(StrokeTrackManager track)
    {
    	if (getBottomY() < track.getTopMostPt().y
    		|| getTopY() > track.getBottomMostPt().y)
    	{
    		return false;
    	}
    	
    	VectorPoint ptLeft = track.getLeftMostPt();
    	int idxStart = getCharIndexByXPos(ptLeft.x);
    	VectorPoint ptRight = track.getRightMostPt();
    	int idxEnd = getCharIndexByXPos(ptRight.x);
    	String log = String.format("sel:%s", line_text.substring(idxStart, idxEnd));
    	Log.d("Text Sel", log);
    	
    	return true;
    }
    
    public int getCharIndexByXPos(float xPos)
    {
    	float absDis = xPos - line_position.x; 
    	float delt = absDis;
    	float charWidth = line_style.getTextSize();
    	int index = 0;
    	while(delt > 0)
    	{
    		int idxDelt = (int)(delt/charWidth);
    		if (idxDelt < 1)
    		{
    			if (delt/charWidth > 0.25)
    			{
    				index += 1;
    			}
    			break;
    		}
    		index += idxDelt;
    		Rect rc = new Rect();
    		line_style.getTextBounds(line_text, 0, index, rc);
    		if (rc.width() >= absDis)
    		{
    			return index;
    		}
    		delt = xPos - rc.width();
    	}
    	
    	return index;
    }
    
	public void setNextTextLine(TextLine nextLine)
	{
		next_textLine = nextLine;
		if (null == nextLine.prev_textline)
		{
			nextLine.setPrevTextLine(this);
		}
	}
	
	public void Draw(Canvas canvas)
	{
		canvas.drawText(line_text, line_position.x, line_position.y, line_style);

		Rect rect = new Rect();
		line_style.getTextBounds(line_text, 0, line_text.length(), rect);
		Log.d("Measure", "Draw: " + line_text + ":width:" + rect.width());
		Log.d("Measure", "Draw: " + line_text + ":height:" + rect.height());

	}

	public boolean inBound(Rect bound) {
		Rect rect = new Rect();
		line_style.getTextBounds(line_text, 0, line_text.length(), rect);
		rect.offset((int)line_position.x, (int)line_position.y);
		if (rect.intersect(bound) || rect.contains(bound) || bound.contains(rect)){
			return true;
		}
		else{
			return false;
		}
	}


	public Pair<String, Integer> getTextInBound(Rect bound){
		String text = "";
		int startIdx = 0;
		for(int i = 0;i < line_text.length() - 1;i++){
			Rect rect = new Rect();
			line_style.getTextBounds(line_text, i, i+1, rect);
			rect.offset((int)line_position.x, (int)line_position.y);
			if (bound.intersect(rect) || bound.contains(rect)){
				if (text.length() < 1) {
					startIdx = i;
				}
				text +=line_text.substring(i,i);
			}
			else if (text.length() > 0){
				break;
			}
		}
		if (text.length() < 1) {
			return  null;
		}
		else{
			return new Pair<String, Integer>(text,startIdx);
		}
	}
}
