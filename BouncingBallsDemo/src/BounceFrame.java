import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BounceFrame extends JFrame {
    private final GameCanvas canvas;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 600;

    private final JLabel labelBallsCount;

    private BallHole hole;

    private int ballsCount = 0;
    private int scoredBallsCount = 0;

    private Random random;

    private ArrayList<BallThread> ballThreads;

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
        JButton buttonAddRed = new JButton("Add red ball");
        JButton buttonAddBlue = new JButton("Add blue ball");
        JButton buttonAddTest1 = new JButton("Add 5x1");
        JButton buttonAddTest2 = new JButton("Add 100x1");
        JButton buttonAddTest3 = new JButton("Add 1000x1");
        JButton buttonJoin = new JButton("Join First");
        JButton buttonStop = new JButton("Stop");
        buttonAddRed.addActionListener(e -> SpawnBall(BallType.RedBall));
        buttonAddBlue.addActionListener(e -> SpawnBall(BallType.BlueBall));
        buttonAddTest1.addActionListener(e -> {
            for (int i = 0; i < 5; i++) {
                SpawnBall(BallType.BlueBall);
            }
            SpawnBall(BallType.RedBall);
        });
        buttonAddTest2.addActionListener(e -> {
            for (int i = 0; i < 100; i++) {
                SpawnBall(BallType.BlueBall);
            }
            SpawnBall(BallType.RedBall);
        });
        buttonAddTest3.addActionListener(e -> {
            for (int i = 0; i < 1000; i++) {
                SpawnBall(BallType.BlueBall);
            }
            SpawnBall(BallType.RedBall);
        });
        buttonJoin.addActionListener(e -> {
            var first = GetBallThreads().get(0);
            for (int i = 1; i < GetBallThreads().size(); i++) {
                GetBallThreads().get(i).WaitForOtherThread(first);
            }
        });
        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonAddRed);
        buttonPanel.add(buttonAddBlue);
        buttonPanel.add(buttonAddTest1);
        buttonPanel.add(buttonAddTest2);
        buttonPanel.add(buttonAddTest3);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonStop);
        buttonPanel.add(labelBallsCount);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public synchronized void SpawnBall(BallType type)
    {

        int y = 35;
        int x = 35;
        /*if(Math.random()<0.5){
            x = GetRandom().nextInt(this.canvas.getWidth());
            y = 0;
        }else{
            x = 0;
            y = GetRandom().nextInt(this.canvas.getHeight());
        }
        */
        Ball b = new Ball(x, y, canvas, GetBallTypeColor(type));
        canvas.add(b);
        int index = GetBallThreads().size();

        BallThread thread = new BallThread(b, hole, () -> {
            canvas.remove(b);
            synchronized (this){
                ballsCount--;
                scoredBallsCount++;
                GetBallThreads().remove(index);
            }
            OnBallCountChanged();
        }, GetBallThreadTypePriority(type));
        GetBallThreads().add(thread);

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

    private Color GetBallTypeColor(BallType type)
    {
        switch (type)
        {
            case RedBall -> { return Color.red; }
            case BlueBall -> { return Color.blue; }
            case GreyBall -> { return Color.gray; }
        }

        return Color.pink;
    }

    private int GetBallThreadTypePriority(BallType type)
    {
        switch (type)
        {
            case RedBall -> { return Thread.MAX_PRIORITY; }
            case BlueBall -> { return Thread.MIN_PRIORITY; }
        }

        return Thread.NORM_PRIORITY;
    }

    private Random GetRandom()
    {
        if(random == null)
            random = new Random();

        return random;
    }

    private ArrayList<BallThread> GetBallThreads()
    {
        if(ballThreads == null)
            ballThreads = new ArrayList<>();

        return ballThreads;
    }

    enum BallType
    {
        RedBall,
        BlueBall,
        GreyBall
    }
}
