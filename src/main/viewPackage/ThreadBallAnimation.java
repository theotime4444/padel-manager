package main.viewPackage;

import javax.swing.*;
import java.awt.*;

public class ThreadBallAnimation extends JPanel {
    private ThreadBall threadBall;
    private int ballX = 0;
    private int ballY = 100;
    private int ballDiameter = 30;
    private boolean movingRight = true;

    public ThreadBallAnimation() {
        setPreferredSize(new Dimension(600, 300));
        setBackground(Color.WHITE);

        threadBall = new ThreadBall(this);
        threadBall.start();
    }

    public void updateBallPosition() {
        if (movingRight) {
            ballX += 5;
            if (ballX + ballDiameter >= getWidth()) {
                movingRight = false;
            }
        } else {
            ballX -= 5;
            if (ballX <= 0) {
                movingRight = true;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
    }
}
