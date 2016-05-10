package com.metalight.xword.utils;

import java.util.ArrayList;
import java.util.List;

import com.metalight.xword.edit_symbols.EditSymbolManager;
import com.metalight.xword.arkediter.PagePanel;

import android.graphics.Canvas;

public class ShapeStrokeManager {

	private List<ShapeStroke> strokes = new ArrayList<ShapeStroke>();
	
	private EditSymbolManager symbolMgr = new EditSymbolManager();
	
	public void AddShapeStroke(ShapeStroke stroke, PagePanel page) {
		if (null != symbolMgr.ParseShapeStroke(stroke, page)){
			strokes.add(stroke);
		}
	}

	public void draw(Canvas canvas) {
		for(ShapeStroke stroke : this.strokes){
			stroke.draw(canvas);
		}
	}

}
