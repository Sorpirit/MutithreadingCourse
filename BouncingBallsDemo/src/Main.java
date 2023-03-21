import CounterTest.CounterTestRunner;
import StringPrinterTest.StringPrinterTestRunner;

import javax.swing.*;

public class Main {

    private enum TestType
    {
        BallGame,
        StringPrinter,
        Counter
    }

    public static void main(String[] args) {

        TestType testType = TestType.StringPrinter;

        switch (testType)
        {
            case BallGame -> {
                BounceFrame frame = new BounceFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setVisible(true);
                System.out.println("Thread name = " + Thread.currentThread().getName());
            }
            case StringPrinter -> {
                StringPrinterTestRunner stringPrinterTestRunner = new StringPrinterTestRunner();
                stringPrinterTestRunner.Run();
            }
            case Counter -> {
                StringBuilder builder = new StringBuilder();
                CounterTestRunner counterTestRunner = new CounterTestRunner();
                for (int i=0; i < 20; i++)
                {
                    long time = counterTestRunner.Run();
                    builder.append(", " + time);
                }
                System.out.println(builder.toString());
            }
        }
    }
}