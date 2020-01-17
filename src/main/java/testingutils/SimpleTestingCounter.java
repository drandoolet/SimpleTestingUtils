package testingutils;

class SimpleTestingCounter implements TestingTime {
    final Class<?> clazz;
    long start, end;
    private long preheat;
    private long suspendedAt = 0;

    SimpleTestingCounter(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void preheat() {
        preheat = System.nanoTime();
        log(String.format("%s: Preheating started.", clazz.getName()));
    }

    public void start() {
        if (start == 0) {
            start = System.nanoTime();

            String preheatSt = "";
            if (preheat != 0) preheatSt = " Preheating has taken " + secondsWithTwoDecimals(start - preheat) + "s.";
            log(String.format("%s: Testing started.%s", clazz.getName(), preheatSt));
        } else {
            try {
                throw new TestingTimeException(clazz.getName() + ": Counter is already started!");
            } catch (TestingTimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void suspend() {
        if (suspendedAt == 0) suspendedAt = System.nanoTime();
        else {
            try {
                throw new TestingTimeException(clazz.getName() + ": Counter is already suspended!");
            } catch (TestingTimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        if (suspendedAt != 0) {
            start += (System.nanoTime() - suspendedAt); // start time is pushed to later
            suspendedAt = 0;
        } else {
            try {
                throw new TestingTimeException(clazz.getName() + ": Counter is not suspended!");
            } catch (TestingTimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (end == 0) {
            end = System.nanoTime();
            log(String.format(
                    "%s: Testing completed. Total time: %s.",
                    clazz.getName(),
                    secondsWithTwoDecimals(end - start)));
        } else {
            try {
                throw new TestingTimeException(clazz.getName() + ": Counter is already stopped!");
            } catch (TestingTimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        start = 0;
        end = 0;
        suspendedAt = 0;
        preheat = 0;
    }

    String secondsWithTwoDecimals(long nanos) {
        return String.format("%.2f", seconds(nanos));
    }

    double seconds(long nanos) {
        return (nanos / 1_000_000_000.00);
    }

    void log(String s) {
        System.out.println(s);
    }
}
