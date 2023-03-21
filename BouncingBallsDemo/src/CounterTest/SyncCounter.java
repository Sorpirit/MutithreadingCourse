package CounterTest;

public class SyncCounter implements Counter {
    private int counter = 0;


    @Override
    public synchronized int get() {
        return counter;
    }

    @Override
    public synchronized void increment(){
        counter++;
    }

    @Override
    public synchronized void decrement(){
        counter--;
    }
}
