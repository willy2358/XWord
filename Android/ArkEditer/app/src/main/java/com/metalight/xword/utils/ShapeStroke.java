package com.metalight.xword.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
//import android.graphics.Rectangle;

import android.util.Log;

public class ShapeStroke {
	private List<VectorPoint> points_track = new ArrayList<VectorPoint>();
	
	public enum Shape_Type{ UNDEFINED, SINGLE_POINT, CLOSE_RING, VERT_LINE, HORZ_LINE}
	
	private Shape_Type shapeType = 	Shape_Type.UNDEFINED;
	
	private int char_width = 20;
	
	public void addTrackPoint(PointF pt)
	{
		VectorPoint vPt = new VectorPoint(pt.x, pt.y);
		
		this.points_track.add(vPt);
	}
	
	
	public void addTrackPoint(VectorPoint pt)
	{
		this.points_track.add(pt);
	}
	
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(5);
		for(int i = 0; i < points_track.size() - 1; i++)
		{
			PointF pt1 = points_track.get(i);
			PointF pt2 = points_track.get(i + 1);
			
			canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
		}
	}
	
	public Shape_Type getShapeType()
	{
		if (this.shapeType == Shape_Type.UNDEFINED)
		{
			parseShapeType();
		}
		return this.shapeType;
	}
	
	public void parseShapeType()
	{
		if(IsSinglePoint())
		{
			this.shapeType = Shape_Type.SINGLE_POINT;
		}
		
		if (IsCloseRing())
		{
			this.shapeType = Shape_Type.CLOSE_RING;
		}
		
		if (IsHorzLine())
		{
			this.shapeType = Shape_Type.HORZ_LINE;
		}
		
		if (IsVertLine())
		{
			this.shapeType = Shape_Type.VERT_LINE;
		}
		
	}
	
	private boolean IsVertLine()
	{
		VectorPoint ptS = this.points_track.get(0);
		VectorPoint ptE = this.points_track.get(this.getPointCount() - 1);
		
		if (Math.abs(ptS.x - ptE.x) < char_width/2
			&& Math.abs(ptS.y - ptE.y) >= char_width/2
			&& Math.abs(ptS.y -ptE.y) < char_width + 5)
		{
			return true;
		}
		return false;
	}
	private boolean IsHorzLine()
	{
		VectorPoint ptS = this.points_track.get(0);
		VectorPoint ptE = this.points_track.get(this.getPointCount() - 1);
		
		if ((Math.abs(ptS.x - ptE.x) >= char_width/2) && (Math.abs(ptS.y - ptE.y) <= char_width/2))
		{
			return true;
		}
		return false;
	}
	
	private boolean IsSinglePoint()
	{
		return this.points_track.size() == 1;
	}
	private boolean IsCloseRing()
	{
		if (this.points_track.size() < 3)
		{
			return false;
		}
		
		VectorPoint pt0 = this.points_track.get(0);
		if (pt0.getDistanceFrom(this.points_track.get(this.getPointCount() - 1)) < 2.0)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isValidEditSymbolPart()
	{
		return true;
	}
	
	public int getPointCount()
	{
		return this.points_track.size();
	}
	
	public VectorPoint getPointByIndex(int index)
	{
		if (index >= 0 && index < this.points_track.size())
		{
			return this.points_track.get(index);
		}
		
		if (index < 0 && Math.abs(index) <= this.points_track.size() )
		{
			return this.points_track.get(this.points_track.size() - Math.abs(index));
		}
		
		return null;
	}
	
	public void reversePoints()
	{
		Collections.reverse(this.points_track);
	}
	
	public void logShapePoints()
	{
		//int type = this.getShapeType();
		String log0 = String.format("new shape: type: %s", this.getShapeType().toString());
		Log.d("Shape Points", log0);
		
		for(int i = 0; i < points_track.size(); i++)
		{
			VectorPoint pt = points_track.get(i);
	    	String log = String.format("%d : %f, %f; %s:%f, %s:%f, turn:%d", i, pt.x, pt.y, pt.horz_dir.toString(),pt.horz_delt,
				      pt.vert_dir.toString(), pt.vert_delt, pt.isTurnPoint()? 1 : 0);
	    	Log.d("point", log);
		}

	}


	public List<PointF> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}


	public Rect getBounds() {
		Rect rect = new Rect(99999, 99999, 0, 0);
		for(int i = 0; i < points_track.size(); i++) {
			VectorPoint pt = points_track.get(i);
//			String log = String.format("%d : %f, %f; %s:%f, %s:%f, turn:%d", i, pt.x, pt.y, pt.horz_dir.toString(),pt.horz_delt,
//					pt.vert_dir.toString(), pt.vert_delt, pt.isTurnPoint()? 1 : 0);
//			Log.d("point", log);
			if (pt.x < rect.left){
				rect.left = (int)pt.x;
			}
			if (pt.x > rect.right){
				rect.right = (int)pt.x;
			}
			if(pt.y < rect.top){
				rect.top = (int)pt.y;
			}
			if(pt.y > rect.bottom){
				rect.bottom = (int)pt.y;

			}
		}

		return rect;
	}
}
