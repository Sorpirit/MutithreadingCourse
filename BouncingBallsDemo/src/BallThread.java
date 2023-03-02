public class BallThread extends Thread{
    private final Ball b;
    private final BallHole hole;
    private final Runnable onComplete;

    private Thread otherThread;

    public BallThread(Ball ball, BallHole hole, Runnable onComplete, int threadPriority){
        this.b = ball;
        this.hole = hole;
        this.onComplete = onComplete;
        this.setPriority(threadPriority);
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                if(otherThread != null)
                {
                    otherThread.join();
                    otherThread = null;
                }

                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
                if(hole != null && hole.checkCollision(b))
                    break;
            }
        } catch(InterruptedException ignored){ }
        finally {
            onComplete.run();
        }
    }

    public void WaitForOtherThread(Thread thread)
    {
        otherThread = thread;
    }

}
