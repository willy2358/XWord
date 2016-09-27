package com.metalight.xword.arkediter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by willy on 2016/9/27.
 */
public class EditResultViewer extends View {

    public EditResultViewer(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint style = new Paint();
        style.setColor(Color.GREEN);
        style.setStrokeWidth(5);
        style.setTextSize(50);
        canvas.drawText("This is preview", 30,30, style);

    }
}


