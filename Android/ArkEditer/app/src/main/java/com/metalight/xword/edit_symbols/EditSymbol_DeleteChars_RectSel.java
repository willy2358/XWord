package com.metalight.xword.edit_symbols;

import android.graphics.Rect;

import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.arkediter.DocPagePanel_Editable;

public class EditSymbol_DeleteChars_RectSel extends EditSymbol {

	public EditSymbol_DeleteChars_RectSel(ShapeStroke stroke, DocPagePanel_Editable page) {
		super(stroke, page);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean IsMyType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsMyPart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public Rect getRect() {
		return this.stroke.getBounds();
	}

}
