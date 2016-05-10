package com.metalight.xword.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.util.Log;

public class MotionTrack {
	
	private List<VectorPoint> track = new ArrayList<VectorPoint>();
	private VectorPoint leftMostPt = new VectorPoint(100000.0f, 0.0f);
	private VectorPoint rightMostPt = new VectorPoint(0.0f, 0.0f);
	private VectorPoint topMostPt = new VectorPoint(0.0f, 100000.0f);
	private VectorPoint bottomMostPt = new VectorPoint(0.0f, 0.0f);
	
	
	private VectorPoint prevPoint = null;
	private VectorPoint testPoint = null;
	
	private boolean prevPointPossibleTurning = false;
	
	//private VectorPoint 
	
	
	public void addSegFirstPoint(PointF pt)
	{
//		if (null == curShapeStroke)
//		{
//			curShapeStroke = new ShapeStroke();
//		}
//		
//		curShapeStroke.addTrackPoint(pt);
		
		VectorPoint vPt = new VectorPoint(pt);
		track.add(vPt);
		
		this.prevPoint = vPt;
	}
	
	public void addSegEndPoint(PointF pt)
	{
		appendTrackPoint(pt);
		ShapeStroke shapeStroke = new ShapeStroke();
		
		for(int i = 0; i < track.size(); i++)
		{
			VectorPoint vPt = track.get(i);
			shapeStroke.addTrackPoint(vPt);
		}
		
		shapeStroke.logShapePoints();
		track.clear();
		this.prevPointPossibleTurning = false;
	}
	
	public void addSegMidPoint(PointF pt)
	{
		VectorPoint vPt = appendTrackPoint(pt);
		
		if (track.size() <= 2)
		{
			this.prevPoint = vPt;
			return;
		}
		
		if (null == prevPoint)
		{
			return;
		}
		
		if (this.prevPointPossibleTurning)
		{
			Log.d("test", "branch1");
			if (this.prevPoint.horz_dir == vPt.horz_dir
				&& this.prevPoint.vert_dir == vPt.vert_dir)
			{
				Log.d("test", "branch2");
				createNewShapeStrokeEndingWithPrevPoint();
			}

		}
		else
		{
			if (vPt.horz_dir == prevPoint.horz_dir
				&& vPt.vert_dir == prevPoint.vert_dir)
			{
				prevPointPossibleTurning = false;
				Log.d("test", "branch3");
			}
			else
			{
				prevPointPossibleTurning = true;
				String log = String.format("%d", track.size());
				Log.d("set turn", log);
			}
		}
		
		this.prevPoint = vPt;
		

		
		
		
		
		
//		if (track.size() < 4)
//		{
//			return;
//		}
//		
//		for(int i = 2; i < track.size() - 1; i++)
//	    {
//	    	if (track.get(i).horz_dir != track.get(i - 1).horz_dir
//	    		&& track.get(i).horz_dir != track.get(i -2).horz_dir
//	    		&& track.get(i).horz_dir == track.get(i + 1).horz_dir)
//	    	{
//	    		track.get(i).setTurnPoint(true);
//	    		continue;
//	    	}
//	    	
//	    	if (track.get(i).vert_dir != track.get(i - 1).vert_dir
//	    		&& track.get(i).vert_dir != track.get(i - 2).vert_dir
//	    		&& track.get(i).vert_dir == track.get(i + 1).vert_dir)
//	    	{
//	    		track.get(i).setTurnPoint(true);
//	    	}
//	    }
//		
//		
//		//setCornerPoints(vPt);
//
//		
//		//lastStrokePoint = vPt;
	}

	private VectorPoint appendTrackPoint(PointF pt) {
		VectorPoint vPt = new VectorPoint(pt);
		if( track.size() > 0)
		{	
			vPt.setMoveDirection(track.get(track.size() - 1));
		}
		
		track.add(vPt);
		return vPt;
	}
	
	public boolean isHorzTurnPoint(VectorPoint testPt, VectorPoint prevPt1, VectorPoint prevPt2, VectorPoint nextPt)
	{
		if (testPt.horz_dir != prevPt1.horz_dir
			&& testPt.horz_dir != prevPt2.horz_dir
			&& testPt.horz_dir == nextPt.horz_dir)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isVertTurnPoint(VectorPoint testPt, VectorPoint prevPt1, VectorPoint prevPt2, VectorPoint nextPt)
	{
		if (testPt.vert_dir != prevPt1.vert_dir
			&& testPt.vert_dir != prevPt2.vert_dir
			&& testPt.vert_dir == nextPt.vert_dir)
		{
			return true;
		}
		
		return false;
	}
	
	public void AddPoint(PointF pt)
	{
		VectorPoint vPt = appendTrackPoint(pt);
		
		setCornerPoints(vPt);
	}
	
	public VectorPoint getLeftMostPt()
	{
		return leftMostPt;
	}
	
	public int getEditType()
	{
		int type = 1;
		
		return type;
	}
	
	public VectorPoint getRightMostPt()
	{
		return rightMostPt;
	}
	
	public VectorPoint getTopMostPt()
	{
		return topMostPt;
	}
	
	public VectorPoint getBottomMostPt()
	{
		return bottomMostPt;
	}
	
	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		for(int i = 0; i < track.size() - 1; i++)
		{
			PointF pt1 = track.get(i);
			PointF pt2 = track.get(i + 1);
			
			canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
		}
	}
	
	public int getPointCount()
	{
		return track.size();
	}
	
	public VectorPoint getPrevTurnPoint(int index)
	{
		for (int i = index - 1; i >= 0; i--)
		{
			VectorPoint pt = track.get(i);
			if (pt.isTurnPoint())
			{
				return pt;
			}
		}
		
		return null;
	}
	
	public VectorPoint getNextTurnPoint(int index)
	{
		for(int i = index + 1; i < track.size(); i++)
		{
			VectorPoint pt = track.get(i);
			if (pt.isTurnPoint())
			{
				return pt;
			}
		}
		
		return null;
	}
	
	@SuppressLint("DefaultLocale")
	public void parseTurnPoints()
	{
		if (track.size() < 1)
		{
			return;
		}
		
	    track.get(0).setTurnPoint(true);
	    track.get(track.size() - 1).setTurnPoint(true);
	    
	    for(int i = 2; i < track.size() - 1; i++)
	    {
	    	if (track.get(i).horz_dir != track.get(i - 1).horz_dir
	    		&& track.get(i).horz_dir != track.get(i -2).horz_dir
	    		&& track.get(i).horz_dir == track.get(i + 1).horz_dir)
	    	{
	    		track.get(i).setTurnPoint(true);
	    		continue;
	    	}
	    	
	    	if (track.get(i).vert_dir != track.get(i - 1).vert_dir
	    		&& track.get(i).vert_dir != track.get(i - 2).vert_dir
	    		&& track.get(i).vert_dir == track.get(i + 1).vert_dir)
	    	{
	    		track.get(i).setTurnPoint(true);
	    	}
	    }
	    
	    filterJitterTurnPoints();
	    
	    for(int i = 0; i < track.size(); i++)
	    {
	    	VectorPoint pt = track.get(i);
	    	String log = String.format("%d : %f, %f; %s:%f, %s:%f, turn:%d", i, pt.x, pt.y, pt.horz_dir.toString(),pt.horz_delt,
	    			      pt.vert_dir.toString(), pt.vert_delt, pt.isTurnPoint()? 1 : 0);
	    	Log.d("Move Track", log);
	    }
	}

	private void setCornerPoints(VectorPoint pt)
	{
		if (pt.x < leftMostPt.x)
		{
			leftMostPt = pt;
		}
		
		if (pt.x > rightMostPt.x)
		{
			rightMostPt = pt;
		}
		
		if (pt.y < topMostPt.y)
		{
			topMostPt = pt;
		}
		
		if (pt.y > bottomMostPt.y)
		{
			bottomMostPt = pt;
		}
	}
	

	
	private void createNewShapeStrokeEndingWithPrevPoint()
	{
		ShapeStroke shape = new ShapeStroke();
		while(track.size() > 2)
		{
			VectorPoint pt = track.get(0);
			shape.addTrackPoint(pt);
			track.remove(0);
		}
		
		track.get(0).setDefaultMoveDir();
		
		shape.logShapePoints();
	}
	
	

	private void filterJitterTurnPoints() {
		for(int i = 1; i < track.size() - 1; i++)
	    {
	    	VectorPoint pt0 = track.get(i);
	    	if (!pt0.isTurnPoint())
	    	{
	    		continue;
	    	}
	    	
	    	VectorPoint pt1 = getPrevTurnPoint(i);
	    	VectorPoint pt2 = getNextTurnPoint(i);
	    	if(null == pt1 || null == pt2)
	    	{
	    		continue;
	    	}
	    	
	    	double a_square = Math.pow(pt1.x - pt2.x, 2.0) + Math.pow(pt1.y - pt2.y, 2.0);
	    	double b = Math.sqrt(Math.pow(pt1.x - pt0.x, 2.0) + Math.pow(pt1.y - pt0.y, 2.0));
	    	double c = Math.sqrt(Math.pow(pt2.x - pt0.x, 2.0) + Math.pow(pt2.y - pt0.y, 2.0));    	
	    	double cos_a = (Math.pow(b, 2.0) + Math.pow(c, 2.0) - a_square)/(2 * b * c);
	    	
	    	if (cos_a <= Math.cos(Math.toRadians(145)) && cos_a >= -1.0)
	    	{
	    		track.get(i).setTurnPoint(false);
	    	}
	    	
	    }
	}
}
