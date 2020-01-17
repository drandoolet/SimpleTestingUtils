package test;

import main.testingutils.MilestoneTestingTime;
import main.testingutils.TestingFactory;
import main.testingutils.TestingTime;

import static java.lang.Thread.sleep;

public class CounterTest {
    public static void main(String[] args) throws InterruptedException {
        testMilestone();
    }

    private static void testSimple() throws InterruptedException {
        TestingTime testingTime = TestingFactory.getTestingCounter(CounterTest.class);

        testingTime.start();
        testingTime.start(); // alr started
        sleep(200);
        testingTime.suspend();
        testingTime.suspend(); // alr susp
        sleep(300);
        testingTime.resume();
        testingTime.resume(); // is not susp
        sleep(200);
        testingTime.stop();
        testingTime.stop(); // alr stop
    }

    private static void testMilestone() throws InterruptedException {
        MilestoneTestingTime testingTime = TestingFactory.getMilestoneTestingCounter(CounterTest.class);

        testingTime.preheat();
        sleep(250);
        testingTime.start();
        sleep(200);
        testingTime.addMilestone("m 1");
        sleep(100);
        testingTime.addMilestone("m 2");
        sleep(300);
        testingTime.stop();
    }
}
