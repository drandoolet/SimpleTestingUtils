package testingutils;

public final class TestingFactory {
    public static TestingTime getTestingCounter(Class<?> c) {
        return new SimpleTestingCounter(c);
    }

    public static MilestoneTestingTime getMilestoneTestingCounter(Class<?> c) {
        return new TestingTimeCounter(c);
    }
}
