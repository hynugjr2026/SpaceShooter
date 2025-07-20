package com.game;

import java.awt.*;

public class PowerUp {
    private int x, y;
    private final int speed = 3;
    private final int width = 30;
    private final int height = 30;
    private PowerUpType type;

    public enum PowerUpType {
        HEALTH, DOUBLE_SHOT, SHIELD
    }

    public PowerUp(int x, int y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics g) {
        switch (type) {
            case HEALTH -> g.setColor(Color.GREEN);
            case DOUBLE_SHOT -> g.setColor(Color.CYAN);
            case SHIELD -> g.setColor(Color.MAGENTA);
        }
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters
    public PowerUpType getType() { return type; }
}
