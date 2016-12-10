package com.metalight.xword.edit_symbols;

import android.graphics.Rect;

import com.metalight.xword.document.elements.TextLine;
import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.arkediter.DocPagePanel_Editable;

import java.util.ArrayList;
import java.util.List;

public abstract class EditSymbol {
	
	protected DocPagePanel_Editable page = null;
	protected ShapeStroke stroke = null;
	protected List<TextLine> _effectedLines = new ArrayList<TextLine>();
	protected boolean _isRemoved = false;
	public abstract boolean IsMyType();
	public abstract boolean IsMyPart();
	public abstract boolean isExecutable();
	public abstract Rect getRect();
	protected List<SymbolCommand> _symbolResults = new ArrayList<SymbolCommand>();
	
	public EditSymbol(ShapeStroke stroke, DocPagePanel_Editable page) {
		this.page = page;
		this.stroke = stroke;
	}

	public void parseSymbolCommmand(){};
	public void addEditResult(SymbolCommand result){
		_symbolResults.add(result);
	}

	public List<SymbolCommand> getEditCommands(){
		return _symbolResults;
	}

	public ShapeStroke getShapeStroke(){
		return stroke;
	}

	public void setRemovedFlag(boolean remove){
		this._isRemoved = remove;
	}
}
