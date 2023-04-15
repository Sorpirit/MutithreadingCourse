package CounterTest;

import StringPrinterTest.StringPrinterThread;

public class CounterTestRunner {
    public long Run(){
        int times = 100000;
        Counter counter = new AtomicCounter();

        Thread incrementThread = new Thread( () -> {
           for (int i = 0; i < times; i++){
               counter.increment();
           }
        });

        Thread decrementThread = new Thread( () -> {
            for (int i = 0; i < times; i++){
                counter.decrement();
            }
        });

        /*Thread printerThread = new Thread( () -> {
            for (int i = 0; i < times; i++){
                System.out.println(counter.get());
            }
        });*/

        long startTime = System.nanoTime();
        incrementThread.start();
        decrementThread.start();
        //printerThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
            //printerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        System.out.println("Counter value:" + counter.get());
        System.out.println("Execution time:" + (endTime - startTime));
        return endTime - startTime;
    }
}
