package CounterTest;

public class NoSyncCounter implements Counter{

    private int counter;

    @Override
    public int get() {
        return counter;
    }

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public void decrement() {
        counter--;
    }
}
