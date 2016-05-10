package com.metalight.xword.utils;

import android.graphics.*;
import android.util.Log;

public class VectorPoint extends PointF {
	
	public Move_Dir horz_dir;
	public Move_Dir vert_dir;
	public float horz_delt;
	public float vert_delt;
	
	private boolean is_turn_point = false;
	
	public enum Move_Dir{ noDelt, toRight, toLeft, toUp, toDown }; 

	public VectorPoint(float x, float y)
	{
		this.x = x;
		this.y = y;
		
		setDefaultMoveDir();
	}
	
	public double getDistanceFrom(VectorPoint pt)
	{
		return Math.sqrt((x - pt.x)*(x - pt.x) - (y - pt.y)*(y - pt.y));
	}
	
	public double getDistanceFrom(PointF pt)
	{
		return Math.sqrt((x - pt.x)*(x - pt.x) - (y - pt.y)*(y - pt.y));
	}
	
	public VectorPoint(PointF pt)
	{
		this.x = pt.x;
		this.y = pt.y;
		setDefaultMoveDir();
	}
	
	public void setDefaultMoveDir()
	{
		horz_dir = Move_Dir.noDelt;
		vert_dir = Move_Dir.noDelt;
	}
	
	public void setTurnPoint(boolean isTurn)
	{
		this.is_turn_point = isTurn;
	}
	
	public boolean isTurnPoint()
	{
		return this.is_turn_point;
	}
	
	public void setMoveDirection(VectorPoint frontPt)
	{
		horz_delt = this.x - frontPt.x;
		if (horz_delt > 0.1)
		{
			horz_dir = Move_Dir.toRight;
		}
		else if (horz_delt < -0.1)
		{
			horz_dir = Move_Dir.toLeft;
		}
		else 
		{
			horz_dir = Move_Dir.noDelt;
		}
		
		vert_delt = this.y - frontPt.y;
		if (vert_delt > 0.1)
		{
			vert_dir = Move_Dir.toDown;
		}
		else if (vert_delt < -0.1)
		{
			vert_dir = Move_Dir.toUp;
		}
		else
		{
			vert_dir = Move_Dir.noDelt;
		}
	}
}
