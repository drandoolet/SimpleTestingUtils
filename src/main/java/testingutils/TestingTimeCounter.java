package testingutils;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

class TestingTimeCounter extends SimpleTestingCounter implements MilestoneTestingTime {
    private SortedMap<Long, String> milestones;

    TestingTimeCounter(Class<?> clazz) {
        super(clazz);
        initMilestones();
    }

    public void addMilestone(String name) {
        milestones.put(System.nanoTime(), name);
    }

    @Override
    public void stop() {
        super.stop();
        log(clazz.getName() + ": Milestones:\n" + milestonesToString());
    }

    @Override
    public void reset() {
        super.reset();
        initMilestones();
    }

    private void initMilestones() {
        milestones = new TreeMap<>();
    }

    private String milestonesToString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Long> iterator = milestones.keySet().iterator();
        int i = 0;
        long prev = start;

        while (iterator.hasNext()) {
            long l = iterator.next();
            builder.append(String.format(
                    "%s: Milestone #%d '%s' took %s s\n",
                    clazz.getName(),
                    ++i,
                    milestones.get(l),
                    secondsWithTwoDecimals(l - prev)));
            prev = l;
        }

        builder.append(String.format(
                "%s: The final milestone took %s s\n",
                clazz.getName(),
                secondsWithTwoDecimals(end - prev)));

        return builder.toString();
    }
}
