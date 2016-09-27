package com.metalight.xword.edit_symbols;


import com.metalight.xword.document.elements.*;
import com.metalight.xword.utils.*;
import com.metalight.xword.arkediter.PagePanel;

import android.graphics.Rect;

public class EditSymbol_DeleteChars_LineSel extends EditSymbol {

	public EditSymbol_DeleteChars_LineSel(ShapeStroke stroke, PagePanel page) {
		super(stroke, page);
	}

	@Override
	public boolean IsMyType() {
		if(ShapeStroke.Shape_Type.HORZ_LINE != stroke.getShapeType()){
			return false;
		}
		
		Rect bound = this.stroke.getBounds();
		TextLine[] lines = page.GetTextLinesInBound(bound);
		
		
		return true;
	}

	@Override
	public boolean IsMyPart() {
		// TODO Auto-generated method stub
		return false;
	}

}
