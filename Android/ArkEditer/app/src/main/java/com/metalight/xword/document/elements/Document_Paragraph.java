package com.metalight.xword.document.elements;

import java.util.ArrayList;
import java.util.List;

import com.metalight.xword.utils.StrokeTrackManager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import org.json.JSONObject;

//paragraph, there is no CR(\r\n) in the paragraph text. 
public class Document_Paragraph {
	private  float FONT_SIZE_FACTOR = 4;
	private List<TextSegment> segments = new ArrayList<TextSegment>();
	private  List<TextLine> _lines = new ArrayList<TextLine>();

	public void parse(String paraText)
	{
		String txtSeg = paraText;
		TextSegment seg = new TextSegment();
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		p.setTextSize(80.0f);

		seg.setText(txtSeg, new PointF(10.0f, 10.0f), p);

		segments.add(seg);

	}

	public  void parse(JSONObject lineBlock){
		try{
			JSONObject jPos = lineBlock.getJSONObject("Position");
			PointF pos = new PointF((float)jPos.getDouble("X") *3.8f, (float)jPos.getDouble("Y") * 3f);
//			if (pos.y > 115){
//				return;
//			}

			JSONObject jRun = lineBlock.getJSONObject("Run");
			float size = (float)jRun.getDouble("FontSize");
			int color = jRun.getInt("Color");
			String text = lineBlock.getString("Text");
			TextLine line = new TextLine(text);
			Paint style = new Paint();

			style.setColor(Color.argb(0xFF, Color.red(color), Color.green(color), Color.blue(color)));
			//Color.parseColor(jRun.getString("Color"));
			style.setTextSize(size * FONT_SIZE_FACTOR);
			line.setPostion(pos, style);
			this._lines.add(line);
		}
		catch (Exception ex){

		}
	}

    public void Draw(Canvas canvas)
    {
/*    	for(TextSegment seg: segments)
    	{
    		seg.Draw(canvas);
    	}*/

		for(TextLine line: _lines){
			line.Draw(canvas);
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
