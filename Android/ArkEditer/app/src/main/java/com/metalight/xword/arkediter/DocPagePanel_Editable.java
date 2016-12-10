package com.metalight.xword.arkediter;
import com.metalight.xword.document.elements.Document_Page;
import com.metalight.xword.document.elements.Document_Paragraph;
import com.metalight.xword.document.elements.TextLine;
import com.metalight.xword.document.types.Document;
import com.metalight.xword.edit_symbols.EditSymbol;
import com.metalight.xword.edit_symbols.EditSymbolManager;
import com.metalight.xword.edit_symbols.EditSymbol_DeleteChars_LineSel;
import com.metalight.xword.edit_symbols.EditSymbol_InsertText;
import com.metalight.xword.edit_symbols.SymbolCommand;
import com.metalight.xword.utils.HttpTask;
import com.metalight.xword.utils.ShapeStrokeManager;
import com.metalight.xword.utils.ShapeStroke;
import com.metalight.xword.utils.StrokeTrack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DocPagePanel_Editable extends DocPagePanel {
	
	private ShapeStrokeManager strokeMgr = new ShapeStrokeManager();
	private EditSymbolManager symbolMgr = new EditSymbolManager();
	private ShapeStroke curStroke = null;
	private boolean _appExit = false;
    private LinkedList<EditSymbol> _unExedSymbols = new LinkedList<EditSymbol>();
	private String _editCommandUrl;
	private EditText _editText;
	private EditSymbol _currentEditSymbol;
	private Map<Integer, List<EditSymbol>> _pageEditSymbols = new HashMap<Integer, List<EditSymbol>>();
	public DocPagePanel_Editable(Context context) {
		super(context);
		final DocPagePanel_Editable page = this;
		setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent event)
			{
				PointF pt = new PointF(event.getX(), event.getY());
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					curStroke = new ShapeStroke();
					curStroke.addTrackPoint(pt);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					curStroke.addTrackPoint(pt);
					if (!isStrokeModifyExistedSymbols(curStroke)){
						EditSymbol editSymbol = symbolMgr.ParseShapeStroke(curStroke, page);
						if (null != editSymbol) {
							recordPageEditSymbol(editSymbol);
							setCurrentEditSymbol(editSymbol);
						}
					}
					curStroke = null;
				}
				else if (event.getAction() == MotionEvent.ACTION_MOVE)
				{
					curStroke.addTrackPoint(pt);
				}
				drawUsersStokeTracks();
				return true;
			}
		});
		CreateThreadToUploadEditCommand();
	}

	public void setInsertedText(String text){
		if (null == text || text.isEmpty()){
			return;
		}
		if(_currentEditSymbol instanceof EditSymbol_InsertText){
			((EditSymbol_InsertText)_currentEditSymbol).setInsertText(text);
		}
		else if (_currentEditSymbol instanceof EditSymbol_DeleteChars_LineSel){
			((EditSymbol_DeleteChars_LineSel)_currentEditSymbol).setReplaceText(text);
		}
	}

	private void recordPageEditSymbol(EditSymbol symbol){
		if (!_pageEditSymbols.containsKey(_currentPageIdx)){
			_pageEditSymbols.put(_currentPageIdx, new ArrayList<EditSymbol>());
		}
		_pageEditSymbols.get(_currentPageIdx).add(symbol);
	}

	private boolean isStrokeModifyExistedSymbols(ShapeStroke stroke){
		ShapeStroke.Shape_Type shpType = stroke.getShapeType();
		if (shpType != ShapeStroke.Shape_Type.VERT_LINE && shpType != ShapeStroke.Shape_Type.HORZ_LINE){
			return false;
		}

		List<EditSymbol> symbols = _pageEditSymbols.get(_currentPageIdx);
		if (null == symbols){
			return false;
		}

		for(EditSymbol sym : symbols){
			Rect symRect = sym.getRect();
			if(symRect.intersect(stroke.getBounds()) || symRect.contains(stroke.getBounds())){
				if (shpType == ShapeStroke.Shape_Type.HORZ_LINE){
					sym.setRemovedFlag(true);
				}
				else if (sym instanceof EditSymbol_DeleteChars_LineSel){
					_currentEditSymbol = sym;
					showTextInputControl(stroke.getPointByIndex(0));

				}
				return  true;
			}
		}
		return false;
	}
	private void showTextInputControl(PointF pt){
		_editText.setVisibility(View.VISIBLE);
		RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams)_editText.getLayoutParams();
		layoutParams2.leftMargin = (int)pt.x;
		layoutParams2.topMargin = (int)pt.y;
		_editText.setLayoutParams(layoutParams2);
	}

	private void setCurrentEditSymbol(EditSymbol symbol){
		this._currentEditSymbol = symbol;
		if (symbol instanceof EditSymbol_InsertText){
			showTextInputControl( ((EditSymbol_InsertText)symbol).getInsertPosition());
		}
		strokeMgr.addStroke(curStroke);
		_unExedSymbols.offer(symbol);

	}
	public  void setEdtiText(EditText edit){
		_editText = edit;
	}
	public  void setEditCommandUrl(String url){
		this._editCommandUrl = url;
	}

	private void CreateThreadToUploadEditCommand() {
		Thread thread=new Thread(new Runnable(){
			@Override
			public void run(){
				while(!_appExit){
					EditSymbol symbol = _unExedSymbols.peek();
					if (null == symbol){
						continue;
					}
					if (symbol.isExecutable()){
						symbol = _unExedSymbols.poll();
						executeEditSymbol(symbol);
					}
					SystemClock.sleep(100);
				}
			}
		});
		thread.start();
	}

	private void executeEditSymbol(final EditSymbol symbol){
		for(SymbolCommand cmd : symbol.getEditCommands()){
			//EditCommand cmd = parseShapeStrokeToEditCommand(symbol);
			HttpTask task = new HttpTask();
			task.setTaskHandler(new HttpTask.HttpTaskHandler() {
				public void taskSuccessful(String json) {
					try {
						JSONObject jObject = new JSONObject(json);
//						if (0 != jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)) {
//							_unExedSymbols.offer(symbol);  //may be successful next time
//						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				public void taskFailed() {
				}
			});

			task.execute(makeEditCommandUrl(cmd ));
		}

	}

	private  String makeEditCommandUrl(SymbolCommand cmd){
		TextLine line = cmd.getAffectedTextLine();
		Document_Paragraph para = line.getParentParaghaph();
		Document_Page page = para.getParentPage();
		Document doc = page.getParentDocument();

		String url = String.format("%sdocId=%d&pageIdx=%d&runId=%d&editType=%d&oldPartText=%s&newPartText=%s&editTrack=%s",
				     _editCommandUrl, doc.getDocumentId(), page.getPageNumber(), line.getRunId(), cmd.getEditType(),
				     cmd.getAffectedTextString(), cmd.getTextStringReplacement(),cmd.getTrackData());
        Log.d("", "makeEditCommandUrl: " + url);
        return url;
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		drawUserEditTracks(canvas);
	}

	private void drawUserEditTracks(Canvas canvas)
	{
		if (null != strokeMgr){
			strokeMgr.draw(canvas);
		}
		
		if (null != curStroke){
		   curStroke.draw(canvas);
		}
	}
	
	private void drawUsersStokeTracks()
	{
		invalidate();
	}
	
	public TextLine[] GetTextLinesInBound(Rect bound) {

		Document_Page page = document.getPage(_currentPageIdx);
		if (null != page)
		{
			return  page.GetTextLinesInBound(bound);
		}
		return  null;
	}

}
