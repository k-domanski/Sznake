package com.example.sznake.gameCore;

import com.example.sznake.utils.Direction;

import java.io.Serializable;

public class QTE implements Serializable {
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

    public boolean checkQTE(Direction direction) {
        if (QTEDirection == direction) {
            return true;
        }
        return false;
    }
}
