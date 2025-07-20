package com.game;

import javax.swing.*;

public class SpaceShooterGame extends JFrame {

    public SpaceShooterGame() {
        setTitle("太空射击游戏");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // 直接调用父类 JFrame 的 add() 方法
        add(new GamePanel());
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpaceShooterGame());
    }
}