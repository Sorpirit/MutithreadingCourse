import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Main {

    public static AtomicInteger roadCounter = new AtomicInteger();

    public static void main(String[] args) {
        int carsCount = 100;
        
        Object roadLock = new Object();
        Lock lightLock = new ReentrantLock();
        Condition greenLightCondition = lightLock.newCondition();
        TrafficLight trafficLight = new TrafficLight(roadLock, lightLock, greenLightCondition);
        Thread threadLight = new Thread(trafficLight);
        Thread[] cars = new Thread[carsCount];
        for (int i = 0; i < carsCount; i++)
        {
            cars[i] = new Thread(new Car(i, roadLock, lightLock, greenLightCondition, trafficLight));
        }

        //Start both threads.
        threadLight.start();
        for (Thread t : cars) {
            t.start();
        }
    }

    public static boolean IsFinished(){
        return roadCounter.get() > 1000;
    }

    public static void CarIncrement(){
        roadCounter.incrementAndGet();
    }
}

class Car implements Runnable {

    private static final int startWait = 2;
    private static final int endWait = 400;

    private int id;
    private Object roadLock;
    private Lock lightLock;
    private Condition greenLightCondition;
    private TrafficLight trafficLight;

    public Car(int id, Object roadLock, Lock lightLock, Condition greenLightCondition, TrafficLight trafficLight) {
        this.id = id;
        this.roadLock = roadLock;
        this.lightLock = lightLock;
        this.greenLightCondition = greenLightCondition;
        this.trafficLight = trafficLight;
    }

    @Override
    public void run() {
        try {
            while (!Main.IsFinished())
            {
                boolean isSuccessful = false;

                while (!isSuccessful)
                {
                    lightLock.lock();
                    while (trafficLight.GetLightType() != LightType.Green)
                        greenLightCondition.await();


                    synchronized (roadLock)
                    {
                        if(trafficLight.GetLightType() == LightType.Green)
                        {
                            isSuccessful = true;
                            Go();
                        }
                    }
                    lightLock.unlock();
                }

                Thread.sleep(endWait);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //lightLock.unlock();
        }
    }

    public void Go() throws InterruptedException {
        Thread.sleep(startWait);
        System.out.println("Drive: " + id);
        Main.CarIncrement();
    }
}

class TrafficLight implements Runnable {

    private int[] delays = new int[] {60, 10, 40, 10};

    private final AtomicReference<LightType> light = new AtomicReference<>(LightType.Green);
    private Object roadLock;
    private Lock lightLock;
    private Condition greenLightCondition;

    public TrafficLight(Object roadLock, Lock lightLock, Condition greenLightCondition) {
        this.roadLock = roadLock;
        this.lightLock = lightLock;
        this.greenLightCondition = greenLightCondition;
    }

    @Override
    public void run() {
        try {
            while (!Main.IsFinished())
            {
                Thread.sleep(delays[light.get().getValue()]);

                lightLock.lock();
                light.set(light.get().next());
                System.out.println(light.get().toString());

                if(light.get() == LightType.Green)
                {
                    greenLightCondition.signalAll();
                }
                lightLock.unlock();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lightLock.unlock();
        }
    }

    //Needs to be used with a road block
    public LightType GetLightType()
    {
        return light.get();
    }
}

enum LightType
{
    Green(0),
    YellowGR(1),
    Red(2),
    YellowRG(3);

    private final int value;
    LightType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public LightType next() {
        int val = (value + 1) % 4;
        switch(val) {
            case 0:
                return Green;
            case 1:
                return YellowGR;
            case 2:
                return Red;
            case 3:
                return YellowRG;
        }
        return this;
    }

    @Override
    public String toString() {
        switch(value) {
            case 0:
                return "Green";
            case 1:
                return "YellowGR";
            case 2:
                return "Red";
            case 3:
                return "YellowRG";
        }

        return "Green";
    }
}