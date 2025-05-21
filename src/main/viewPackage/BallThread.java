package main.viewPackage;

public class BallThread extends Thread {
    private BallPanel panel;
    private static final int FRAME_RATE = 30;

    public BallThread(BallPanel panel) {
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