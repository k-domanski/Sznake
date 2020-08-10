package com.example.sznake;

public class PowerUp extends GameField {

    int m_color = 0xFFFFFF00;
    public PowerUp(int x, int y) {
        super(x, y);
    }

    public PowerUp() {
    }

    public void setColor(int color)
    {
        m_color = color;
    }
}
