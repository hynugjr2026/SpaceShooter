package com.game;

import java.awt.*;

public class Enemy {
    private int x, y;
    private int speed;
    private int health;
    private final int width = 40;
    private final int height = 40;

    public Enemy(int x, int y, int speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}