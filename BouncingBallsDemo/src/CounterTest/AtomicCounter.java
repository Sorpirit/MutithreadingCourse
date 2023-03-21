package CounterTest;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter{

    private final AtomicInteger counter;

    public AtomicCounter() {
        this(0);
    }

    public AtomicCounter(int value) {
        this.counter = new AtomicInteger(value);
    }

    @Override
    public int get() {
        return counter.get();
    }

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public void decrement() {
        counter.decrementAndGet();
    }
}
