import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class Main {
    public static void main(String[] args) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ExecutorService threadPool = Executors.newFixedThreadPool(4, threadFactory);

        for (int i = 0; i < 5; i++) {
            threadPool.execute(new Task());
        }

        threadPool.shutdown();
    }
}

class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("Task executed by thread: " + Thread.currentThread().getName());
    }
}