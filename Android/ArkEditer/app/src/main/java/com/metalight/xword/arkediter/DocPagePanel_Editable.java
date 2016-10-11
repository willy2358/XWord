package com.metalight.xword.arkediter;


import com.metalight.xword.document.elements.Document_Page;
import com.metalight.xword.document.elements.TextLine;
import com.metalight.xword.document.types.Document;
import com.metalight.xword.utils.ShapeStrokeManager;
import com.metalight.xword.utils.ShapeStroke;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.*;

public class DocPagePanel_Editable extends DocPagePanel {
	
	private ShapeStrokeManager strokeMgr = new ShapeStrokeManager();
	
	private ShapeStroke curStroke = null;
	
	public DocPagePanel_Editable(Context context)
	{
		super(context);
		
		final DocPagePanel_Editable page = this;
		setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent event)
			{
				PointF pt = new PointF(event.getX(), event.getY());
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					curStroke = new ShapeStroke();
					curStroke.addTrackPoint(pt);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					curStroke.addTrackPoint(pt);
					strokeMgr.AddShapeStroke(curStroke, page);
					curStroke = null;
				}
				else if (event.getAction() == MotionEvent.ACTION_MOVE)
				{
					curStroke.addTrackPoint(pt);
				}
				drawUsersStokeTracks();
				return true;
			}
		});
		
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		drawUserEditTracks(canvas);
	}

	private void drawUserEditTracks(Canvas canvas)
	{
		if (null != strokeMgr){
			strokeMgr.draw(canvas);
		}
		
		if (null != curStroke){
		   curStroke.draw(canvas);
		}
	}
	
	private void drawUsersStokeTracks()
	{
		invalidate();
	}
	
	public TextLine[] GetTextLinesInBound(Rect bound) {

		Document_Page page = document.getCurrentPage();
		if (null != page)
		{
			return  page.GetTextLinesInBound(bound);
		}
		return  null;
	}

}
