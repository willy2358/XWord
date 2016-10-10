package com.metalight.xword.edit_symbols;

import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.arkediter.DocPagePanel_Editable;

public abstract class EditSymbol {
	
	protected DocPagePanel_Editable page = null;
	protected ShapeStroke stroke = null;
	
	public abstract boolean IsMyType();
	public abstract boolean IsMyPart();
	
	public EditSymbol(ShapeStroke stroke, DocPagePanel_Editable page) {
		this.page = page;
		this.stroke = stroke;
	}
}
