public class Main {

    private static Object sharedStateLock = new Object();
    private static String strState = "z";

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (getStrState().equals("s")) {
                    setStrState("z");
                } else {
                    setStrState("s");
                }
                synchronized (sharedStateLock)
                {
                    sharedStateLock.notify();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            while(getStrState().equals("z")) {
                try {
                    synchronized (sharedStateLock)
                    {
                        sharedStateLock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 100; i >= 0; i--) {
                System.out.println(i);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(getStrState().equals("z"))
                    return;
            }
        });

        threadA.start();
        threadB.start();
    }

    public static String getStrState(){
        String var = "";
        synchronized (strState)
        {
            var = strState;
        }
        return var;
    }

    public static void setStrState(String str){
        synchronized (strState)
        {
            strState = str;
        }
    }
}