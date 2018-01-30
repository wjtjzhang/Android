package com.lele.fivelinkball.com.lele.fivelinkball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FiveLinkBallView extends View {

    private Paint mPaint;
    private int cellWidth;
    private int cellHeight;

    public FiveLinkBallView(Context context) {
        super(context);
        init();
    }

    public FiveLinkBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FiveLinkBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        super.onDraw(canvas);
    }

    private void drawGrid(Canvas canvas) {
        cellWidth = this.getWidth() / 9;
        cellHeight = this.getHeight() / 9;

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(i*cellWidth, 0, i*cellWidth, 9*cellWidth, mPaint);
        }

        for (int j = 0; j < 10; j++) {
            canvas.drawLine(0, j*cellWidth, 9*cellWidth, j*cellWidth, mPaint);
        }
    }
}
