package com.metalight.xword.edit_symbols;


import com.metalight.xword.document.elements.*;
import com.metalight.xword.utils.*;
import com.metalight.xword.arkediter.DocPagePanel_Editable;

import android.graphics.Rect;
import android.util.Pair;

import java.util.Arrays;

public class EditSymbol_DeleteChars_LineSel extends EditSymbol {

	private String _replaceText;
	public EditSymbol_DeleteChars_LineSel(ShapeStroke stroke, DocPagePanel_Editable page) {
		super(stroke, page);
	}

	@Override
	public boolean IsMyType() {
		if(ShapeStroke.Shape_Type.HORZ_LINE != stroke.getShapeType()){
			return false;
		}
		
		Rect bound = this.stroke.getBounds();
		TextLine[] lines = page.GetTextLinesInBound(bound);
		if (null == lines || lines.length < 1){
			return  false;
		}

        _effectedLines.addAll(Arrays.asList(lines));
        parseSymbolCommmand();
		return true;
	}

	@Override
	public void parseSymbolCommmand(){
        if (_symbolResults.size() < 1){
            Rect bound = this.stroke.getBounds();
            TextLine line = _effectedLines.get(0);
            Pair<String, Integer> ret = line.getTextInBound(bound);
            String uniqueSubStr = StringUtil.getUniqueSubString(line.getText(), ret.first, ret.second);
            String newStr = uniqueSubStr.replace(ret.first, "");
            SymbolCommand symRet = new SymbolCommand(this, line, uniqueSubStr, newStr);
            addEditResult(symRet);
        }
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
		Rect rect = this.stroke.getBounds();
		rect.top = (int)this._effectedLines.get(0).getTopY();
		rect.bottom = (int)this._effectedLines.get(0).getBottomY();
		return rect;
	}

	public void setReplaceText(String replaceText){
		_replaceText = replaceText;
	}

}
