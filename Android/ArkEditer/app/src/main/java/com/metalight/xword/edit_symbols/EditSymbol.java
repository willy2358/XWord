package com.metalight.xword.edit_symbols;

import java.util.List;

import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.arkediter.PagePanel;

import android.graphics.PointF;

public abstract class EditSymbol {
	
	protected PagePanel page = null;
	protected ShapeStroke stroke = null;
	
	public abstract boolean IsMyType();
	public abstract boolean IsMyPart();
	
	public EditSymbol(ShapeStroke stroke, PagePanel page) {
		this.page = page;
		this.stroke = stroke;
	}
}
