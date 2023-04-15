import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int N = 5;
        ArrayList<String> sharedList = new ArrayList<>();
        ArrayList<String>[] threadStrLists = new ArrayList[N];
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; i++) {
            threadStrLists[i] = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                threadStrLists[i].add("Thread " + i + " value " + j);
            }
        }

        for (int i = 0; i < N; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                int listListIndex = finalI;
                for (String s : threadStrLists[listListIndex]) {
                    synchronized (sharedList) {
                        sharedList.add(s);
                    }
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (String s : sharedList) {
            System.out.println(s);
        }
    }
}