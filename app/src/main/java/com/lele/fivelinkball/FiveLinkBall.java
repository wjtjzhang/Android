package com.lele.fivelinkball;

import java.util.ArrayList;
import java.util.List;

public class FiveLinkBall {

    public static final int STEP = 10;
    public static int BLANK = -1;
    public int[][] grid;
    private ArrayList<Ball> openList = new ArrayList<Ball>();
    private ArrayList<Ball> closeList = new ArrayList<Ball>();
    private ArrayList<Ball> emptyBalls = new ArrayList<Ball>();
    private int rows;
    private int cols;
    private int colors;

    public FiveLinkBall(int rows, int cols, int colors) {
        this.rows = rows;
        this.cols = cols;
        this.colors = colors;
        reset();
        addRandomColorsBall(3);
    }

    public static Ball find(List<Ball> balls, Ball point) {
        for (Ball n : balls)
            if ((n.x == point.x) && (n.y == point.y)) {
                return n;
            }
        return null;
    }

    public static boolean exists(List<Ball> balls, Ball ball) {
        for (Ball n : balls) {
            if ((n.x == ball.x) && (n.y == ball.y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean exists(List<Ball> balls, int x, int y) {
        for (Ball n : balls) {
            if ((n.x == x) && (n.y == y)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Ball startBall = new Ball(5, 1);
        Ball endBall = new Ball(5, 5);
        FiveLinkBall fiveLinkBall = new FiveLinkBall(9, 9, 5);
        Ball parent = fiveLinkBall.findPath(startBall, endBall);

        for (int i = 0; i < fiveLinkBall.getGrid().length; i++) {
            for (int j = 0; j < fiveLinkBall.getGrid()[0].length; j++) {
                System.out.print(fiveLinkBall.getGrid()[i][j] + ", ");
            }
            System.out.println();
        }
        ArrayList<Ball> arrayList = new ArrayList<Ball>();

        while (parent != null) {
            arrayList.add(new Ball(parent.x, parent.y));
            parent = parent.parent;
        }
        System.out.println("\n");

        for (int i = 0; i < fiveLinkBall.getGrid().length; i++) {
            for (int j = 0; j < fiveLinkBall.getGrid()[0].length; j++) {
                if (exists(arrayList, i, j)) {
                    System.out.print("@, ");
                } else {
                    System.out.print(fiveLinkBall.getGrid()[i][j] + ", ");
                }

            }
            System.out.println();
        }

    }

    private void reset() {
        grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = BLANK;
                emptyBalls.add(new Ball(i, j));
            }
        }
    }

    public void addRandomColorsBall(int num) {

        for (int i = 0; i < num; i++) {
            if (!emptyBalls.isEmpty()) {
                int position = (int) (Math.random() * emptyBalls.size());
                Ball ball = emptyBalls.get(position);
                grid[ball.x][ball.y] = (int) (Math.random() * colors);
                emptyBalls.remove(position);
            }
        }
        /*for (int i=0;i<8;i++){
            grid[i][3]=(int)(Math.random()* colors);
        }*/
    }

    public Ball findMinFBallInOpenList() {
        Ball tempBall = openList.get(0);
        for (Ball ball : openList) {
            if (ball.F < tempBall.F) {
                tempBall = ball;
            }
        }
        return tempBall;
    }

    public ArrayList<Ball> findNeighborBalls(Ball currentBall) {
        ArrayList<Ball> arrayList = new ArrayList<Ball>();
        int topX = currentBall.x;
        int topY = currentBall.y - 1;
        if (canReach(topX, topY) && !exists(closeList, topX, topY)) {
            arrayList.add(new Ball(topX, topY));
        }
        int bottomX = currentBall.x;
        int bottomY = currentBall.y + 1;
        if (canReach(bottomX, bottomY) && !exists(closeList, bottomX, bottomY)) {
            arrayList.add(new Ball(bottomX, bottomY));
        }
        int leftX = currentBall.x - 1;
        int leftY = currentBall.y;
        if (canReach(leftX, leftY) && !exists(closeList, leftX, leftY)) {
            arrayList.add(new Ball(leftX, leftY));
        }
        int rightX = currentBall.x + 1;
        int rightY = currentBall.y;
        if (canReach(rightX, rightY) && !exists(closeList, rightX, rightY)) {
            arrayList.add(new Ball(rightX, rightY));
        }
        return arrayList;
    }

    public boolean canReach(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            return grid[x][y] == BLANK;
        }
        return false;
    }

    public Ball findPath(Ball startBall, Ball endBall) {
        openList.add(startBall);
        while (openList.size() > 0) {
            Ball currentBall = findMinFBallInOpenList();
            openList.remove(currentBall);
            closeList.add(currentBall);
            ArrayList<Ball> neighborBalls = findNeighborBalls(currentBall);
            for (Ball ball : neighborBalls) {
                if (exists(openList, ball)) {
                    foundPoint(currentBall, ball);
                } else {
                    notFoundPoint(currentBall, endBall, ball);
                }
            }
            if (find(openList, endBall) != null) {
                return find(openList, endBall);
            }
        }
        return find(openList, endBall);
    }

    private void foundPoint(Ball tempStart, Ball ball) {
        int G = calcG(tempStart, ball);
        if (G < ball.G) {
            ball.parent = tempStart;
            ball.G = G;
            ball.calcF();
        }
    }

    private void notFoundPoint(Ball tempStart, Ball end, Ball ball) {
        ball.parent = tempStart;
        ball.G = calcG(tempStart, ball);
        ball.H = calcH(end, ball);
        ball.calcF();
        openList.add(ball);
    }

    private int calcG(Ball start, Ball ball) {
        int G = STEP;
        int parentG = ball.parent != null ? ball.parent.G : 0;
        return G + parentG;
    }

    private int calcH(Ball end, Ball ball) {
        int step = Math.abs(ball.x - end.x) + Math.abs(ball.y - end.y);
        return step * STEP;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int[][] getGrid() {
        return grid;
    }

    public static class Ball {
        public int x;
        public int y;
        public int F;
        public int G;
        public int H;
        public Ball parent;

        public Ball(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void calcF() {
            this.F = this.G + this.H;
        }
    }
}
