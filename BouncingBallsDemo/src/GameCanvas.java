import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;

public class GameCanvas extends JPanel {
    private final HashSet<DrawableEntity> balls = new HashSet<>();
    private final LinkedList<DrawableEntity> toRemove = new LinkedList<>();

    public synchronized void add(DrawableEntity b){
        this.balls.add(b);
    }
    public synchronized void remove(DrawableEntity b){
        this.toRemove.add(b);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (DrawableEntity b : balls) {
            b.draw(g2);
        }

        processRemoved();
    }

    private synchronized void processRemoved()
    {
        for (DrawableEntity b : toRemove) {
            balls.remove(b);
        }
        toRemove.clear();
    }
}
