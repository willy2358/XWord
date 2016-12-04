package com.metalight.xword.edit_symbols;

import com.metalight.xword.document.elements.TextLine;

/**
 * Created by willy on 2016/10/16.
 */
public class SymbolCommand {

    private TextLine _textLine;

    public  int DocId;
    public  int PageIdx;
    public  int RunId;
    public  int EditType;
    private   String _oldText;
    private   String _newText;
    public  String TrackData;
    private EditSymbol _editSybmol;

    public SymbolCommand(EditSymbol symbol, TextLine line, String oldSubText, String subTextRelace){
        _editSybmol = symbol;
        _textLine = line;
        _oldText = oldSubText;
        _newText = subTextRelace;
    }

    public String getTrackData(){
        return "";
    }

    public int getEditType(){
        return 3;
    }

    public TextLine getAffectedTextLine(){
        return _textLine;
    }

    public String getAffectedTextString(){
        return _oldText;
    }

    public String getTextStringReplacement(){
        return _newText;
    }
//    public  int getDocId() {
//        return 0;
//    }
//
//    public  int getPageId(){
//        return  0;
//    }
//
//    public int getRunId()
}
