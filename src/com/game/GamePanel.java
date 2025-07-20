package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<PowerUp> powerUps;
    private Timer timer;
    private int score;
    private boolean gameOver;
    private Random random;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);

        random = new Random();
        initGame();
        addKeyListener(new KeyHandler());

        timer = new Timer(16, this); // ≈60 FPS
        timer.start();
    }

    private void initGame() {
        player = new Player(375, 500);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        powerUps = new ArrayList<>();
        score = 0;
        gameOver = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制游戏元素
        player.draw(g);
        bullets.forEach(b -> b.draw(g));
        enemies.forEach(e -> e.draw(g));
        powerUps.forEach(p -> p.draw(g));

        // 绘制UI
        drawUI(g);

        if (gameOver) {
            drawGameOver(g);
        }
    }

    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("分数: " + score, 20, 30);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("游戏结束", 300, 300);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("按R键重新开始", 300, 350);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            updateGame();
        }
        repaint();
    }

    private void updateGame() {
        player.update();
        updateBullets();
        updateEnemies();
        updatePowerUps();
        checkCollisions();
        spawnEnemies();
        spawnPowerUps();
    }

    private void updateBullets() {
        bullets.forEach(Bullet::update);
        bullets.removeIf(b -> b.getY() < 0);
    }

    private void updateEnemies() {
        enemies.forEach(Enemy::update);
        enemies.removeIf(e -> e.getY() > 600);
    }

    private void updatePowerUps() {
        powerUps.forEach(PowerUp::update);
        powerUps.removeIf(p -> p.getBounds().getY() > 600);
    }

    private void checkCollisions() {
        // 子弹与敌人碰撞
        for (Bullet bullet : new ArrayList<>(bullets)) {
            Rectangle bulletBounds = bullet.getBounds();
            for (Enemy enemy : new ArrayList<>(enemies)) {
                if (bulletBounds.intersects(enemy.getBounds())) {
                    bullets.remove(bullet);
                    enemy.setHealth(enemy.getHealth() - 1);
                    if (enemy.getHealth() <= 0) {
                        enemies.remove(enemy);
                        score += 10;
                    }
                    break;
                }
            }
        }

        // 玩家与敌人碰撞
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), 50, 30);
        for (Enemy enemy : new ArrayList<>(enemies)) {
            if (playerBounds.intersects(enemy.getBounds())) {
                gameOver = true;
                break;
            }
        }

        // 玩家与道具碰撞
        for (PowerUp powerUp : new ArrayList<>(powerUps)) {
            if (playerBounds.intersects(powerUp.getBounds())) {
                powerUps.remove(powerUp);
                // 根据道具类型处理效果
                switch (powerUp.getType()) {
                    case HEALTH -> { /* 增加生命值 */ }
                    case DOUBLE_SHOT -> { /* 双发子弹 */ }
                    case SHIELD -> { /* 护盾 */ }
                }
                break;
            }
        }
    }

    private void spawnEnemies() {
        if (random.nextInt(100) < 2) { // 2%的几率生成敌人
            int x = random.nextInt(760);
            int speed = random.nextInt(3) + 1;
            int health = random.nextInt(2) + 1;
            enemies.add(new Enemy(x, -40, speed, health));
        }
    }

    private void spawnPowerUps() {
        if (random.nextInt(500) < 1) { // 0.2%的几率生成道具
            int x = random.nextInt(770);
            PowerUp.PowerUpType type = PowerUp.PowerUpType.values()[
                    random.nextInt(PowerUp.PowerUpType.values().length)];
            powerUps.add(new PowerUp(x, -30, type));
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                bullets.add(new Bullet(player.getX() + 25, player.getY()));
            }
            if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
                initGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.keyReleased(KeyEvent.VK_LEFT);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.keyReleased(KeyEvent.VK_RIGHT);
            }
        }
    }
}