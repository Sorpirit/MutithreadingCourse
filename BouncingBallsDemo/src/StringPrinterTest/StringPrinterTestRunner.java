package StringPrinterTest;

public class StringPrinterTestRunner {

    public void Run(){

        StringPrinterThread thread1 = new StringPrinterThread(100, "-");
        StringPrinterThread thread2 = new StringPrinterThread(100, "|");

        thread1.setDependantThread(thread2);
        thread2.setDependantThread(thread1);

        thread1.start();
    }

}
