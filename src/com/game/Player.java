package com.game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private final int speed = 5;
    private boolean leftPressed, rightPressed;
    private final int width = 50;
    private final int height = 30;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (leftPressed) x = Math.max(0, x - speed);
        if (rightPressed) x = Math.min(750, x + speed);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
}
