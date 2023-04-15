package StringPrinterTest;

import java.util.concurrent.atomic.AtomicBoolean;

public class StringPrinterThread extends Thread{

    private final Object lock;
    private final String targetChar;
    private final StringBuilder lineBuilder;
    private final AtomicBoolean controlBool;
    private final int charsPreLine;
    private final boolean targetValue;

    public StringPrinterThread(Object lock, String targetChar, StringBuilder lineBuilder, AtomicBoolean controlBool, boolean targetValue, int charsPreLine) {
        this.lock = lock;
        this.targetChar = targetChar;
        this.lineBuilder = lineBuilder;
        this.controlBool = controlBool;
        this.charsPreLine = charsPreLine;
        this.targetValue = targetValue;
    }

    @Override
    public void run() {
        for (int j = 0; j < charsPreLine; j++) {
            synchronized (lock) {
                while (controlBool.get() != targetValue) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lineBuilder.append(targetChar);
                controlBool.set(!targetValue);
                lock.notifyAll();
            }
        }
    }
}
