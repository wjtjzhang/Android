package com.lele.fivelinkball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lele.fivelinkball.Ball.Ball;

public class FiveLinkBallView extends View {

    int colors[] = {Color.RED, Color.BLUE, Color.BLACK, Color.GRAY, Color.YELLOW, Color.GREEN};
    private Paint mPaint;
    private int cellWidth;
    private int cellHeight;
    private FiveLinkBall fiveLinkBall = new FiveLinkBall(9, 9, colors.length);
    private int level = 0;
    private int preBallColorIndex[] = new int[3];
    private Ball selectedBall = new Ball(-1, -1);

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
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        generatePreBalls();
        fiveLinkBall.addRandomColorsBall(preBallColorIndex);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBall(canvas);
        drawPreShowBall(canvas);
        super.onDraw(canvas);
    }

    private void generatePreBalls() {
        for (int i = 0; i < 3; i++) {
            preBallColorIndex[i] = (int) (Math.random() * colors.length);
        }
    }

    private void drawPreShowBall(Canvas canvas) {
        cellWidth = this.getWidth() / fiveLinkBall.getCols();
        for (int i = 0; i < 3; i++) {
            mPaint.setColor(colors[preBallColorIndex[i]]);
            canvas.drawCircle((i + 3) * cellWidth + cellWidth / 2, 9 * cellWidth + cellWidth / 1.3f, cellWidth / 2.5f, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) Math.floor(event.getX() / cellWidth * 1f);
        int y = (int) Math.floor(event.getY() / cellWidth * 1f);
        if (x < fiveLinkBall.getRows() && y < fiveLinkBall.getCols()) {
            if (fiveLinkBall.getGrid()[x][y] != FiveLinkBall.BLANK) {
                selectedBall.x = x;
                selectedBall.y = y;
            } else if (fiveLinkBall.getGrid()[x][y] == FiveLinkBall.BLANK) {
                Ball startBall = selectedBall;
                Ball endBall = new Ball(x, y);
                Ball parent = fiveLinkBall.findPath(startBall, endBall);
                if (parent != null) {
                    //TO DO : Move animation
                    int currentColor = fiveLinkBall.getGrid()[startBall.x][startBall.y];
                    fiveLinkBall.getGrid()[startBall.x][startBall.y] = FiveLinkBall.BLANK;
                    fiveLinkBall.getGrid()[endBall.x][endBall.y] = currentColor;
                    fiveLinkBall.addRandomColorsBall(preBallColorIndex);
                    generatePreBalls();
                }
                selectedBall.x = -1;
                selectedBall.y = -1;
            }
            postInvalidate();
        }
        return super.onTouchEvent(event);
    }

    private void drawBall(Canvas canvas) {
        cellWidth = this.getWidth() / fiveLinkBall.getCols();
        for (int i = 0; i < fiveLinkBall.getRows(); i++) {
            for (int j = 0; j < fiveLinkBall.getCols(); j++) {
                int colorIndex = fiveLinkBall.getGrid()[i][j];
                if (colorIndex != FiveLinkBall.BLANK) {
                    mPaint.setColor(colors[colorIndex]);
                    canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 2.5f, mPaint);

                    if (selectedBall.x != -1 && selectedBall.x == i && selectedBall.y == j) {
                        mPaint.setColor(Color.WHITE);
                        canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 5f, mPaint);
                    }
                }
            }
        }
    }

    private void drawGrid(Canvas canvas) {
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