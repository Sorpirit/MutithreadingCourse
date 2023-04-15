package StringPrinterTest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StringPrinterTestRunner {

    private int linesCount = 20;
    private int charsPreLine = 100;
    private String lineBuilder = "";

    public void Run(){
        Object lock = new Object();
        /*AtomicBoolean atomicBoolean = new AtomicBoolean();
        for (int i = 0; i < linesCount; i++) {
            StringBuilder lineBuilder = new StringBuilder();
            Thread t1 = new Thread(new StringPrinterThread(lock, "-", lineBuilder, atomicBoolean, true, charsPreLine));
            Thread t2 = new Thread(new StringPrinterThread(lock, "|", lineBuilder, atomicBoolean, false, charsPreLine));
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String line = lineBuilder.toString();
            System.out.println(i + " : " + line);
        }*/


        for (int i = 0; i < linesCount; i++) {
            lineBuilder = "";
            Thread t1 = new Thread(() -> {
                for (int j = 0; j < charsPreLine; j++) {
                    lineBuilder += "-";
                }
            });
            Thread t2 = new Thread(() -> {
                for (int j = 0; j < charsPreLine; j++) {
                    lineBuilder += "|";
                }
            });
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String line = lineBuilder.toString();
            System.out.println(i + " : " + line);
        }
    }
}
