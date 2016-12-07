package com.metalight.xword.edit_symbols;

import java.util.ArrayList;
import java.util.List;

import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.arkediter.DocPagePanel_Editable;

public class EditSymbolManager {
	
	private List<EditSymbol> _possibleSymbols = new ArrayList<EditSymbol>();
	
	public EditSymbol ParseShapeStroke(ShapeStroke stroke, DocPagePanel_Editable page){
		
		EditSymbol symbol = TestWhetherAnWholeSymbol(stroke, page);
		if (null != symbol){
			this._possibleSymbols.clear();
			return symbol;
		}
		return TestWhetherPartOnAnySymbol(stroke, page);
	}
	
	private EditSymbol TestWhetherPartOnAnySymbol(ShapeStroke stroke, DocPagePanel_Editable page) {
		EditSymbol symbol = new EditSymbol_DeleteChars_LineSel(stroke, page);
		if (symbol.IsMyPart()){
			_possibleSymbols.add(symbol);
		}
		
		symbol = new EditSymbol_SwapText(stroke, page);
		if (symbol.IsMyPart()){
			_possibleSymbols.add(symbol);
		}
		
		if (this._possibleSymbols.size() > 0){
			return this._possibleSymbols.get(0);
		}
		else{
			return null;
		}
	}

	private EditSymbol TestWhetherAnWholeSymbol(ShapeStroke stroke, DocPagePanel_Editable page){
		
		EditSymbol symbol = new EditSymbol_DeleteChars_LineSel(stroke, page);
		if (symbol.IsMyType()){
			return symbol;
		}
		
		symbol = new EditSymbol_SwapText(stroke, page);
		if (symbol.IsMyType()){
			return symbol;
		}

		symbol = new EditSymbol_InsertText(stroke, page);
		if (symbol.IsMyType()){
			return symbol;
		}
		return null;
	}
	

}
