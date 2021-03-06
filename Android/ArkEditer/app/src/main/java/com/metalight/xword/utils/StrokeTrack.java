package com.metalight.xword.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

public class StrokeTrack {
	
	private List<ShapeStroke> strokes = new ArrayList<ShapeStroke>();
	
	private VectorPoint ptEnd1 = null;
	private VectorPoint ptEnd2 = null;
	
	private final double NEAR_POINT_DISTANCE = 5.0f;
	
	public boolean addStroke(ShapeStroke stroke)
	{
		if (!stroke.isValidEditSymbolPart())
		{
			return false;
		}
		
		if (!canAcceptSubsequentTypeStroke(stroke))
		{
			return false;
		}
		
		if (stroke.getShapeType() == ShapeStroke.Shape_Type.CLOSE_RING)
		{
			this.strokes.add(stroke);
			return true;
		}
		
		if (this.strokes.size() < 1)
		{
			
		}
		
		
		boolean ret = false;
		if (this.strokes.size() < 1)
		{
			setEndPoint(1, stroke.getPointByIndex(0));
			setEndPoint(2, stroke.getPointByIndex(-1));
			this.strokes.add(stroke);
			ret = true;
		}
		else
		{
			ret = concatenateNewStrokeSeg(stroke);
		}
		
		
		return ret;
	}

	public void draw(Canvas canvas) {
		
		for(ShapeStroke stroke : strokes)
		{
			stroke.draw(canvas);
		}
		
	}
	
	public boolean canAcceptSubsequentTypeStroke(ShapeStroke stroke)
	{
		if (this.strokes.size() < 1)
		{
			return true;
			
		}
		return true;
	}
	
	public boolean isValidEditSymbols()
	{
		return true;
	}
	

	
	private void setEndPoint(int index, VectorPoint pt)
	{
		if (index <= 1)
		{
			this.ptEnd1 = pt;
		}
		else
		{
			this.ptEnd2 = pt;
		}
	}
	
	private boolean concatenateNewStrokeSeg(ShapeStroke stroke)
	{
		if (ptEnd2.getDistanceFrom(stroke.getPointByIndex(0)) <= NEAR_POINT_DISTANCE)
		{
			this.strokes.add(stroke);
			ptEnd2 = stroke.getPointByIndex(-1);
			return true;
		}
		
		if (ptEnd2.getDistanceFrom(stroke.getPointByIndex(-1)) <= NEAR_POINT_DISTANCE)
		{
			stroke.reversePoints();
			this.strokes.add(stroke);
			ptEnd2 = stroke.getPointByIndex(-1);
			return true;
		}
		
		if (ptEnd1.getDistanceFrom(stroke.getPointByIndex(-1)) <= NEAR_POINT_DISTANCE)
		{
			this.strokes.add(0, stroke);
			ptEnd1 = stroke.getPointByIndex(0);
			return true;
		}
		
		if (ptEnd1.getDistanceFrom(stroke.getPointByIndex(0)) <= NEAR_POINT_DISTANCE)
		{
			stroke.reversePoints();
			this.strokes.add(0, stroke);
			ptEnd1 = stroke.getPointByIndex(0);
			return true;
		}
		
		return false;
	}
}
