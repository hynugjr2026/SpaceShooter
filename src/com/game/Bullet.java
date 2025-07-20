package com.game;

import java.awt.*;

public class Bullet {
    private int x, y;
    private final int speed = 7;
    private final int width = 5;
    private final int height = 15;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y -= speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters
    public int getY() { return y; }
}
