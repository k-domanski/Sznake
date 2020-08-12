package com.example.sznake;

public class QTE {
    private long stopTime;
    private Direction QTEDirection;

    public Direction getQTEDirection() {
        return QTEDirection;
    }

    public QTE(long timeInMillis) {
        this.stopTime = System.currentTimeMillis() + timeInMillis;
        QTEDirection= Direction.getRandomElement();
    }

    public QTE() {

        this.stopTime = System.currentTimeMillis() + 3000;
        QTEDirection= Direction.getRandomElement();
    }

    public boolean isQTEActive() {
        return System.currentTimeMillis() < this.stopTime;
    }

    public boolean checkQTE(int X, int Y) {
        if (QTEDirection == Direction.UP && X == 1) {
            return true;
        } else if (QTEDirection == Direction.DOWN && X == -1) {
            return true;
        } else if (QTEDirection == Direction.LEFT && Y == 1) {
            return true;
        } else return QTEDirection == Direction.RIGHT && Y == -1;

    }
}
