import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private final GameCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    private final JLabel labelBallsCount;

    private final BallHole hole;

    private int ballsCount = 0;
    private int scoredBallsCount = 0;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new GameCanvas();

        this.hole = new BallHole(WIDTH / 2, HEIGHT / 2);
        this.canvas.add(hole);

        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        labelBallsCount = new JLabel("Balls count: 0  ");
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        buttonStart.addActionListener(e -> SpawnBall());
        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(labelBallsCount);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public synchronized void SpawnBall()
    {
        Ball b = new Ball(canvas);
        canvas.add(b);

        BallThread thread = new BallThread(b, hole, () -> {
            canvas.remove(b);
            ballsCount--;
            scoredBallsCount++;
            OnBallCountChanged();
        });
        thread.start();
        System.out.println("Thread name = " +
                thread.getName());

        ballsCount++;
        OnBallCountChanged();
    }

    public synchronized void OnBallCountChanged()
    {
        labelBallsCount.setText("Active balls count:" + ballsCount + ". Scored balls count: " + scoredBallsCount);
    }
}
