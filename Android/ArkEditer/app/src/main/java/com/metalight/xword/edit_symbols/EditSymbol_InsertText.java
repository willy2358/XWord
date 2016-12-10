package com.metalight.xword.edit_symbols;

import android.graphics.Rect;
import android.util.Pair;

import com.metalight.xword.arkediter.DocPagePanel_Editable;
import com.metalight.xword.document.elements.TextLine;
import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.utils.StringUtil;
import com.metalight.xword.utils.VectorPoint;

import java.util.Arrays;
import java.util.List;

/**
 * Created by willy on 2016/12/7.
 */
public class EditSymbol_InsertText extends EditSymbol {

    private String _insertText;
    public EditSymbol_InsertText(ShapeStroke stroke, DocPagePanel_Editable page) {
        super(stroke, page);
    }
    @Override
    public boolean IsMyType() {
        Rect bound = this.stroke.getBounds();
        TextLine[] lines = page.GetTextLinesInBound(bound);
        if (null == lines || lines.length != 1){
            return  false;
        }

        if (this.stroke.getShapeType() == ShapeStroke.Shape_Type.VERT_LINE){
            _effectedLines.addAll(Arrays.asList(lines));
            return true;
        }

        List<VectorPoint> turns = stroke.getTurnPoints();
        if (turns.size() == 3){
            VectorPoint pt1 = turns.get(0);
            VectorPoint pt2 = turns.get(1);
            VectorPoint pt3 = turns.get(2);
            if (pt2.y < pt1.y && pt2.y < pt3.y
                    && pt1.x < pt2.x && pt2.x < pt3.x){
                _effectedLines.addAll(Arrays.asList(lines));
                return  true;
            }
        }
        return false;
    }
    @Override
    public boolean IsMyPart() {
        return false;
    }

    @Override
    public boolean isExecutable() {
        return _insertText != null &&  !_insertText.isEmpty();
    }

    @Override
    public Rect getRect() {
        return this.stroke.getBounds();
    }

    public void setInsertText(String text){
        this._insertText = text;
        parseSymbolCommmand();
    }
    @Override
    public void parseSymbolCommmand() {
        if (this.stroke.getShapeType() != ShapeStroke.Shape_Type.VERT_LINE){
            parseDownArrowInsertSymbol();
        }
        else {
            parseVerticalLineInsertSymbol();
        }
    }

    public  VectorPoint getInsertPosition(){
        if (this.stroke.getShapeType() != ShapeStroke.Shape_Type.VERT_LINE){
            return this.stroke.getPointByIndex(stroke.getPointCount()/2);
        }
        else{
            return  stroke.getTurnPoints().get(1);
        }

    }


    private  void parseVerticalLineInsertSymbol(){
        VectorPoint pt = this.stroke.getPointByIndex(this.stroke.getPointCount()/2);
        Rect bound = this.stroke.getBounds();
        TextLine line = _effectedLines.get(0);
        int idx = line.getCharIndexByXPos(pt.x);
        String text = line.getText().substring(idx, idx + 5);
        String uniqueSubStr = StringUtil.getUniqueSubString(line.getText(), text, idx);
        String newStr = _insertText + uniqueSubStr;
        SymbolCommand symRet = new SymbolCommand(this, line, uniqueSubStr, newStr);
        addEditResult(symRet);
    }

    private void parseDownArrowInsertSymbol() {
        Rect bound = this.stroke.getBounds();
        TextLine line = _effectedLines.get(0);
        Pair<String, Integer> ret = line.getTextInBound(bound);
        String uniqueSubStr = StringUtil.getUniqueSubString(line.getText(), ret.first, ret.second);

        List<VectorPoint> turns = stroke.getTurnPoints();
        VectorPoint insertPt = turns.get(1);
        ShapeStroke frontShape = new ShapeStroke();
        for(int i = 0; i < 2; i++){
            frontShape.addTrackPoint(turns.get(i));
        }
        Rect bound1 = frontShape.getBounds();
        Pair<String, Integer> front = line.getTextInBound(bound1);

        ShapeStroke backShp = new ShapeStroke();
        backShp.addTrackPoint(turns.get(1));
        backShp.addTrackPoint(turns.get(2));
        Rect bound2 = backShp.getBounds();
        Pair<String, Integer> back = line.getTextInBound(bound2);
    }
}
