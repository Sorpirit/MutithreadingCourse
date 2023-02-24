import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BallHole implements DrawableEntity {
    private static final int XSIZE = 30;
    private static final int YSIZE = 30;
    private static final int RADIUS_SQUARED = XSIZE * XSIZE / 4;

    private int x;
    private int y;


    public BallHole(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public void draw (Graphics2D g2){
        g2.setColor(Color.green);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public boolean checkCollision(Ball ball){
        return Math.pow(ball.getX() - x, 2) + Math.pow(ball.getY() - y, 2) <= (Ball.RADIUS_SQUARED + RADIUS_SQUARED);
    }

    @Override
    public int hashCode() {
        return -1;
    }
}
