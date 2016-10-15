package com.metalight.xword.arkediter;
import com.metalight.xword.document.elements.Document_Page;
import com.metalight.xword.document.elements.TextLine;
import com.metalight.xword.edit_symbols.EditSymbol;
import com.metalight.xword.edit_symbols.EditSymbolManager;
import com.metalight.xword.utils.ErrorCode;
import com.metalight.xword.utils.HttpTask;
import com.metalight.xword.utils.ShapeStrokeManager;
import com.metalight.xword.utils.ShapeStroke;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class DocPagePanel_Editable extends DocPagePanel {
	
	private ShapeStrokeManager strokeMgr = new ShapeStrokeManager();
	private EditSymbolManager symbolMgr = new EditSymbolManager();
	private ShapeStroke curStroke = null;
	private boolean _appExit = false;
    private LinkedList<EditSymbol> _unExedSymbols = new LinkedList<EditSymbol>();
	private String _editCommandUrl;
	public DocPagePanel_Editable(Context context)
	{
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
					EditSymbol editSymbol = symbolMgr.ParseShapeStroke(curStroke, page);
					if (null != editSymbol) {
						strokeMgr.addStroke(curStroke);
					}
					_unExedSymbols.offer(editSymbol);
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

	public  void setEditCommandUrl(String url){
		this._editCommandUrl = url;
	}

	private void CreateThreadToUploadEditCommand() {
		Thread thread=new Thread(new Runnable(){
			@Override
			public void run(){
				while(!_appExit){
					EditSymbol symbol = _unExedSymbols.poll();
					if (null != symbol){
						executeStrokeEditCommand(symbol);
					}
					Log.d("TEST", "run: ");
					SystemClock.sleep(100);
				}
			}
		});
		thread.start();
	}

	private void executeStrokeEditCommand(final EditSymbol symbol){
		EditCommand cmd = parseShapeStrokeToEditCommand(symbol);
		HttpTask task = new HttpTask();
		task.setTaskHandler(new HttpTask.HttpTaskHandler() {
			public void taskSuccessful(String json) {
				try {
					JSONObject jObject = new JSONObject(json);
					if (0 != jObject.getString("ErrorMsg").compareToIgnoreCase(ErrorCode.ERROR_OK)) {
						_unExedSymbols.offer(symbol);  //may be successful next time
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			public void taskFailed() {
			}
		});

		task.execute(makeEditCommandUrl(cmd ));
	}

	private  String makeEditCommandUrl(EditCommand cmd){
		String url = String.format("%sdocId=%d&pageIdx=%d&runId%d&editType=%d&oldPartText=%s&newPartText=%s&editTrack=%s",
				     _editCommandUrl, cmd.DocId, cmd.PageIdx, cmd.RunId, cmd.EditType, cmd.OldText, cmd.NewText,cmd.TrackData);

		return url;
	}
	private EditCommand parseShapeStrokeToEditCommand(EditSymbol symbol){
		return  new EditCommand();
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
