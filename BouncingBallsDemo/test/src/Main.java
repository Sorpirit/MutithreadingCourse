import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 4;
        int taskSize = 500;
        int numElements = 50000;
        double[] array = new double[numElements];
        for (int i = 0; i < numElements; i++) {
            array[i] = Math.random();
        }
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int numTasks = numElements / taskSize;
        for (int i = 0; i < numTasks; i++) {
            int start = i * taskSize;
            int end = (i + 1) * taskSize;
            executor.submit(new SumTask(array, start, end));
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("Sum: " + SumTask.getSum());
    }
}

class SumTask implements Runnable {
    private final double[] array;
    private final int start;
    private final int end;
    private static double sum = 0;

    public SumTask(double[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        double localSum = 0;
        for (int i = start; i < end; i++) {
            localSum += array[i];
        }
        synchronized (SumTask.class) {
            sum += localSum;
        }
    }

    public static double getSum() {
        return sum;
    }
}