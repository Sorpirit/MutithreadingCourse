package StringPrinterTest;

public class StringPrinterThread extends Thread{

    private int times;
    private String string;
    private Thread depandantThread;

    public StringPrinterThread(int times, String string) {
        this.times = times;
        this.string = string;
    }

    public StringPrinterThread(int times, String string, Thread depandantThread) {
        this(times, string);
        this.depandantThread = depandantThread;
    }

    public void setDependantThread(Thread depandantThread)
    {
        this.depandantThread = depandantThread;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < times) {

            System.out.print(string);
            i++;

            if(depandantThread != null)
            {
                synchronized (depandantThread)
                {
                    if(depandantThread.getState() == State.NEW)
                    {
                        depandantThread.start();
                    }else{
                        depandantThread.notify();
                    }
                }

                if(depandantThread.getState() != State.TERMINATED)
                    WaitForNotification();
            }
        }

        synchronized (depandantThread)
        {
            if(depandantThread.getState() != State.TERMINATED)
                depandantThread.notify();
        }
    }

    public synchronized void WaitForNotification(){
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
