package com.metalight.xword.arkediter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.metalight.xword.document.types.Document;

/**
 * Created by willy on 2016/9/27.
 */
public class EditResultViewer extends View {

    private Document document = null;

    public EditResultViewer(Context context){
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if (null != document){
            this.document.draw(canvas);
        }

    }

    public void setDocument(Document doc) {
        this.document = doc;
    }
}


