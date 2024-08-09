package telran.time;

public enum TimeUnit {
    SECOND(1), MINUTE(60), HOUR(3600);

    private int valueOfSeconds;

    TimeUnit(int valueOfSeconds) {
        this.valueOfSeconds = valueOfSeconds;
    }

    public int getValueOfSeconds() {
        return valueOfSeconds;
    }

    public float between(TimePoint p1, TimePoint p2) {
        return (p2.getAmount() * p2.getTimeUnit().getValueOfSeconds()
                - p1.getAmount() * p1.getTimeUnit().getValueOfSeconds()) / this.valueOfSeconds;
    }
}
