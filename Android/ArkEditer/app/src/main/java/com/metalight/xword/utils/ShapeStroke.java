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
	private List<VectorPoint> turn_points = null;
	
	public enum Shape_Type{ UNDEFINED, SINGLE_POINT, CLOSE_RING, VERT_LINE, HORZ_LINE}
	
	private Shape_Type shapeType = 	Shape_Type.UNDEFINED;
	
	private int char_width = 20;
	
	public void addTrackPoint(PointF pt)
	{
		VectorPoint vPt = new VectorPoint(pt.x, pt.y);
		if( points_track.size() > 0)
		{
			vPt.setMoveDirection(points_track.get(points_track.size() - 1));
		}
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
			/*&& Math.abs(ptS.y -ptE.y) < char_width + 5*/)
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

	public  List<VectorPoint> getTurnPoints(){
		if (null == this.turn_points){
			parseTurnPoints();
		}
		return turn_points;
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

	public void parseTurnPoints()
	{
		if (points_track.size() < 1)
		{
			return;
		}
		points_track.get(0).setTurnPoint(true);
		points_track.get(points_track.size() - 1).setTurnPoint(true);

		for(int i = 2; i < points_track.size() - 1; i++)
		{
			if (points_track.get(i).horz_dir != points_track.get(i - 1).horz_dir
					&& points_track.get(i).horz_dir != points_track.get(i -2).horz_dir
					&& points_track.get(i).horz_dir == points_track.get(i + 1).horz_dir)
			{
				points_track.get(i).setTurnPoint(true);
				continue;
			}

			if (points_track.get(i).vert_dir != points_track.get(i - 1).vert_dir
					&& points_track.get(i).vert_dir != points_track.get(i - 2).vert_dir
					&& points_track.get(i).vert_dir == points_track.get(i + 1).vert_dir)
			{
				points_track.get(i).setTurnPoint(true);
			}
		}

		filterJitterTurnPoints();
        turn_points = new ArrayList<VectorPoint>();
		for(int i = 0; i < points_track.size(); i++)
		{
			VectorPoint pt = points_track.get(i);
			String log = String.format("%d : %f, %f; %s:%f, %s:%f, turn:%d", i, pt.x, pt.y, pt.horz_dir.toString(),pt.horz_delt,
					pt.vert_dir.toString(), pt.vert_delt, pt.isTurnPoint()? 1 : 0);
			Log.d("Move Track", log);
			if (pt.isTurnPoint()){
				turn_points.add(pt);
			}
		}
	}
	private void filterJitterTurnPoints() {
		for(int i = 1; i < points_track.size() - 1; i++)
		{
			VectorPoint pt0 = points_track.get(i);
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
				points_track.get(i).setTurnPoint(false);
			}

		}
	}
	public VectorPoint getPrevTurnPoint(int index)
	{
		for (int i = index - 1; i >= 0; i--)
		{
			VectorPoint pt = points_track.get(i);
			if (pt.isTurnPoint())
			{
				return pt;
			}
		}

		return null;
	}

	public VectorPoint getNextTurnPoint(int index)
	{
		for(int i = index + 1; i < points_track.size(); i++)
		{
			VectorPoint pt = points_track.get(i);
			if (pt.isTurnPoint())
			{
				return pt;
			}
		}

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
