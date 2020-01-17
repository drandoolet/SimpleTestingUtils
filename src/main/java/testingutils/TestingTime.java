package testingutils;

public interface TestingTime {
    void preheat();

    void start();

    void suspend();

    void resume();

    void stop();

    void reset();
}
