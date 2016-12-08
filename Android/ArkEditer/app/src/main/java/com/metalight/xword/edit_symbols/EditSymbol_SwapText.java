package com.metalight.xword.edit_symbols;

import android.graphics.Rect;
import android.util.Pair;

import com.metalight.xword.document.elements.TextLine;
import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.arkediter.DocPagePanel_Editable;
import com.metalight.xword.utils.StringUtil;
import com.metalight.xword.utils.VectorPoint;

import java.util.Arrays;
import java.util.List;

public class EditSymbol_SwapText extends EditSymbol {

	public EditSymbol_SwapText(ShapeStroke stroke, DocPagePanel_Editable page) {
		super(stroke, page);

	}

	@Override
	public boolean IsMyType() {
		Rect bound = this.stroke.getBounds();
		TextLine[] lines = page.GetTextLinesInBound(bound);
		if (null == lines || lines.length != 1){
			return  false;
		}

		List<VectorPoint> turns = stroke.getTurnPoints();
		if (turns.size() < 4){
			return  false;
		}

		_effectedLines.addAll(Arrays.asList(lines));
		parseSymbolCommmand();
		return  true;
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
	public void parseSymbolCommmand(){
		Rect bound = this.stroke.getBounds();
		TextLine line = _effectedLines.get(0);
		Pair<String, Integer> ret = line.getTextInBound(bound);
		String uniqueSubStr = StringUtil.getUniqueSubString(line.getText(), ret.first, ret.second);

		List<VectorPoint> turns = stroke.getTurnPoints();
		ShapeStroke tmpShape = new ShapeStroke();
		for(int i = 0; i < 3; i++){
			tmpShape.addTrackPoint(turns.get(i));
		}
		Rect bound1 = tmpShape.getBounds();
		Pair<String, Integer> front = line.getTextInBound(bound1);

		ShapeStroke tmpShape2 = new ShapeStroke();
		for(int i = turns.size() - 1; i > turns.size() - 4; i-- ){
			tmpShape2.addTrackPoint(turns.get(i));
		}
		Rect bound2 = tmpShape2.getBounds();
		Pair<String, Integer> behind = line.getTextInBound(bound2);
		String frontStr = front.first;
		String behindStr = behind.first;
		if (frontStr.endsWith(behindStr.substring(0, 1)) && uniqueSubStr.indexOf(frontStr + behindStr) < 0){
			frontStr = frontStr.substring(0, frontStr.length() - 1);
		}
		String newStr = uniqueSubStr.replace(frontStr + behindStr, behindStr + frontStr );
		SymbolCommand symRet = new SymbolCommand(this, line, uniqueSubStr, newStr);
		addEditResult(symRet);
	}
}
