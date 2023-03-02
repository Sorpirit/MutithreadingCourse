import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball implements DrawableEntity {

    public static final Object lock = new Object();
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    public static final int RADIUS_SQUARED = XSIZE * XSIZE / 4;
    private static int idCounter;

    private final int id;
    private final Component canvas;

    private final Color color;

    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;

    public Ball(int x, int y, Component c, Color color){
        synchronized (lock)
        {
            id = idCounter++;
        }

        this.color = color;
        this.canvas = c;
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
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
