package com.lele.fivelinkball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FiveLinkBallView extends View {

    int colors[] = {Color.RED, Color.BLUE, Color.BLACK, Color.GRAY, Color.YELLOW, Color.GREEN};
    private Paint mPaint;
    private int cellWidth;
    private int cellHeight;
    private FiveLinkBall fiveLinkBall = new FiveLinkBall(9, 9, colors.length);
    private int level = 0;

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
        drawBall(canvas, fiveLinkBall);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        fiveLinkBall.addRandomColorsBall(3);
        postInvalidate();
        return super.onTouchEvent(event);
    }

    private void drawBall(Canvas canvas, FiveLinkBall fiveLinkBall) {
        cellWidth = this.getWidth() / fiveLinkBall.getCols();
        for (int i = 0; i < fiveLinkBall.getRows(); i++) {
            for (int j = 0; j < fiveLinkBall.getCols(); j++) {
                int colorIndex = fiveLinkBall.getGrid()[i][j];
                if (colorIndex != FiveLinkBall.BLANK) {
                    mPaint.setColor(colors[colorIndex]);
                    canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 2.5f, mPaint);
                }
            }
        }
    }

    private void drawGrid(Canvas canvas, FiveLinkBall fiveLinkBall) {
        mPaint.setColor(Color.GRAY);
        cellWidth = this.getWidth() / fiveLinkBall.getCols();

        for (int i = 0; i <= fiveLinkBall.getCols(); i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, fiveLinkBall.getCols() * cellWidth, mPaint);
        }

        for (int j = 0; j <= fiveLinkBall.getRows(); j++) {
            canvas.drawLine(0, j * cellWidth, fiveLinkBall.getRows() * cellWidth, j * cellWidth, mPaint);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }
}