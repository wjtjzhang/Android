package com.lele.fivelinkball;

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
    private FiveLinkBall fiveLinkBall = new FiveLinkBall(9, 9 , 5);

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
        drawGrid(canvas, fiveLinkBall);
        super.onDraw(canvas);
    }

    private void drawGrid(Canvas canvas, FiveLinkBall grid) {
        cellWidth = this.getWidth() / grid.getCols();
        cellHeight = this.getHeight() / grid.getRows();

        for (int i = 0; i <=grid.getCols(); i++) {
            canvas.drawLine(i*cellWidth, 0, i*cellWidth, grid.getCols()*cellWidth, mPaint);
        }

        for (int j = 0; j <=grid.getRows(); j++) {
            canvas.drawLine(0, j*cellWidth, grid.getRows()*cellWidth, j*cellWidth, mPaint);
        }
    }
}
