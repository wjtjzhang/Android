package com.lele.fivelinkball.Ball;

public class Ball {
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