public class BallThread extends Thread{
    private final Ball b;
    private final BallHole hole;
    private final Runnable onComplete;

    public BallThread(Ball ball, BallHole hole, Runnable onComplete){
        b = ball;
        this.hole = hole;
        this.onComplete = onComplete;
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
                if(hole.checkCollision(b))
                    break;
            }
        } catch(InterruptedException ignored){ }
        finally {
            onComplete.run();
        }
    }


}
