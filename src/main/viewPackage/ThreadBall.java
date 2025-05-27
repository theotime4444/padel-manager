package main.viewPackage;

public class ThreadBall extends Thread {
    private static final int FRAME_RATE = 30;
    private ThreadBallAnimation panel;

    public ThreadBall(ThreadBallAnimation panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            panel.updateBallPosition();
            try {
                Thread.sleep(1000 / FRAME_RATE);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}